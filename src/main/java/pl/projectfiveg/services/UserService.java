package pl.projectfiveg.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.client.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.*;
import pl.projectfiveg.exceptions.DeviceNotFoundException;
import pl.projectfiveg.exceptions.UnAuthException;
import pl.projectfiveg.exceptions.ValidationProjectException;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.Role;
import pl.projectfiveg.repositories.IUserRepository;
import pl.projectfiveg.services.interfaces.ITokenPrivateKey;
import pl.projectfiveg.services.interfaces.IUserService;
import pl.projectfiveg.services.interfaces.IUserServiceInterface;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.services.update.interfaces.IDeviceUpdateService;
import pl.projectfiveg.validators.AuthValidator;

import java.util.Set;

@Service
public class UserService implements IUserService, UserDetailsService, ITokenPrivateKey, IUserServiceInterface {

    private final IUserRepository userRepository;
    private final AuthValidator authValidator;
    private final IDeviceQueryService deviceQueryService;
    private final IDeviceUpdateService deviceUpdateService;
    private final ITaskQueryService taskQueryService;
    private final PasswordEncoder passwordEncoder;
    private final WebSocketClientService webSocketClientService;
    @Value("${Algorithm-key}")
    private String applicationKey;
    @Value("${Admin-salt}")
    private Long ADMIN_SALT;
    private final Long MAX_SALT = 4000000L;
    private final Long MIN_SALT = 1000000L;

    @Autowired
    public UserService(IUserRepository userRepository , AuthValidator authValidator , IDeviceQueryService deviceQueryService , IDeviceUpdateService deviceUpdateService , ITaskQueryService taskQueryService , PasswordEncoder passwordEncoder , WebSocketClientService webSocketClientService) {
        this.userRepository = userRepository;
        this.authValidator = authValidator;
        this.deviceQueryService = deviceQueryService;
        this.deviceUpdateService = deviceUpdateService;
        this.taskQueryService = taskQueryService;
        this.passwordEncoder = passwordEncoder;
        this.webSocketClientService = webSocketClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    @Override
    public String getKey() {
        return applicationKey;
    }

    @Override
    public UserDetails accountVerifyToken(String username , Long salt) {
        User userDetails = getUserByLogin(username);
        if ( salt.equals(userDetails.getSalt()) && userDetails.isEnabled() ) return loadUserByUsername(username);
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity <UserDTO> register(RegisterDTO register) {
        try {
            authValidator.validateRegister(register);
        } catch ( ValidationProjectException ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        User user = new User(register.getLogin() , passwordEncoder.encode(register.getPassword()) , Role.ROLE_WEB_CLIENT , generateSalt());
        String token = generateJwt(user.getUsername() , user.getSalt());
        return new ResponseEntity(userRepository.save(user).toUserDTO(token) , HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ResponseEntity <UserDTO> login(LoginDTO login) {
        User user;
        try {
            user = getUserByLogin(login.getLogin());
        } catch ( UsernameNotFoundException ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        try {
            authValidator.validateLogin(passwordEncoder , login.getPassword() , user.getPassword());
        } catch ( UnAuthException ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        if ( !user.isAdmin() ) {
            user.setSalt(generateSalt());
            User updated = userRepository.save(user);
            String token = generateJwt(updated.getUsername() , updated.getSalt());
            return new ResponseEntity <>(updated.toUserDTO(token) , HttpStatus.OK);
        }
        String token = generateJwt(user.getUsername() , user.getSalt());
        return new ResponseEntity <>(user.toUserDTO(token) , HttpStatus.OK);
    }

    @Override
    public ResponseEntity <UserDTO> deviceLogin(DeviceLoginDTO login) {
        User user;
        try {
            user = getUserByLogin(login.getLogin());
        } catch ( UsernameNotFoundException ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        try {
            authValidator.validateLogin(passwordEncoder , login.getPassword() , user.getPassword() , user.getDeviceType());
        } catch ( UnAuthException ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }

        if ( !Strings.isNullOrEmpty(login.getUuid()) ) {
            Device device;
            try {
                device = deviceQueryService.getDeviceByUuid(login.getUuid());
                authValidator.validateDeviceType(user.getDeviceType() , device.getDeviceType());
            } catch ( DeviceNotFoundException | ValidationProjectException ex ) {
                return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
            }
            Set <TaskDTO> tasks = taskQueryService.getTasksByDeviceUuid(login.getUuid());
            device.updateStatus(tasks);
            deviceUpdateService.update(device);
            return new ResponseEntity(new DeviceAuthDTO(generateJwt(user.getUsername() , user.getSalt()) , device.getUuid()) , HttpStatus.OK);
        }
        if ( Strings.isNullOrEmpty(login.getName()) ) {
            return new ResponseEntity("Device name is required" , HttpStatus.BAD_REQUEST);
        }
        Device device = deviceUpdateService.createDevice(user.getDeviceType() , login.getName());
        try {
            webSocketClientService.webSocketGlobal(device.toDeviceStatusMessage());
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return new ResponseEntity(new DeviceAuthDTO(generateJwt(user.getUsername() , user.getSalt()) , device.getUuid()) , HttpStatus.OK);
    }

    @Override
    public User getUserByLogin(String login) throws UsernameNotFoundException {
        return userRepository.findByLoginOpt(login).orElseThrow(() -> new UsernameNotFoundException("User doesnt exist in system"));
    }

    //@EventListener(ApplicationReadyEvent.class)
    public void injectAccounts() {
        Set <User> users = Set.of(
                new User("linux" , passwordEncoder.encode("linuxlinux") , Role.ROLE_LINUX , generateSalt()) ,
                new User("ios" , passwordEncoder.encode("iosios") , Role.ROLE_IOS , generateSalt()) ,
                new User("android" , passwordEncoder.encode("androidandroid") , Role.ROLE_ANDROID , generateSalt()) ,
                new User("serveradmin" , passwordEncoder.encode("S$Ad&m@ldk51") , Role.ROLE_ADMIN , ADMIN_SALT));
        userRepository.saveAll(users);
    }


    private String generateJwt(String login , Long salt) {
        Algorithm algorithm = Algorithm.HMAC512(applicationKey);
        return JWT.create().withClaim("login" , login).withClaim("salt" , String.valueOf(salt)).sign(algorithm);
    }

    private Long generateSalt() {
        return Math.round((Math.floor(Math.random() * (MAX_SALT - MIN_SALT + 1) + MIN_SALT)));
    }

}
