package pl.projectfiveg.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.DTO.UserDTO;
import pl.projectfiveg.exceptions.ValidationProjectException;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.Role;
import pl.projectfiveg.repositories.IUserRepository;
import pl.projectfiveg.services.interfaces.ITokenPrivateKey;
import pl.projectfiveg.services.interfaces.IUserService;
import pl.projectfiveg.services.interfaces.IUserServiceInterface;
import pl.projectfiveg.validators.AuthValidator;

@Service
public class UserService implements IUserService, UserDetailsService, ITokenPrivateKey, IUserServiceInterface {

    private final IUserRepository userRepository;
    private final AuthValidator authValidator;
    @Value("${Algorithm-key}")
    private String applicationKey;
    private final Long MAX_SALT = 4000000L;
    private final Long MIN_SALT = 1000000L;

    @Autowired
    public UserService(IUserRepository userRepository , AuthValidator authValidator) {
        this.userRepository = userRepository;
        this.authValidator = authValidator;
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

        User user = new User(register.getLogin() , register.getPassword() , Role.ROLE_WEB_CLIENT , generateSalt());
        String token = generateJwt(user.getUsername() , user.getSalt());
        return new ResponseEntity(userRepository.save(user).toUserDTO(token) , HttpStatus.CREATED);
    }

    private User getUserByLogin(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    private String generateJwt(String login , Long salt) {
        Algorithm algorithm = Algorithm.HMAC512(applicationKey);
        return JWT.create().withClaim("login" , login).withClaim("salt" , String.valueOf(salt)).sign(algorithm);
    }

    private Long generateSalt() {
        return Math.round((Math.floor(Math.random() * (MAX_SALT - MIN_SALT + 1) + MIN_SALT)));
    }

}
