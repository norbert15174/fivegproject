package pl.projectfiveg.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.exceptions.UnAuthException;
import pl.projectfiveg.exceptions.ValidationProjectException;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.repositories.IUserRepository;

@Component
public class AuthValidator {

    private final IUserRepository userRepository;
    private final String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";

    @Autowired
    public AuthValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateRegister(RegisterDTO registerDTO) {
        if ( userRepository.findByLoginOpt(registerDTO.getLogin()).isPresent() ) {
            throw new ValidationProjectException("User already exist");
        }
        if ( !registerDTO.getPassword().matches(pattern) ) {
            throw new ValidationProjectException("Password is too weak, it should be 8 characters long and contains at least: 1 uppercase letter, 1 special sign, 1 number");
        }
    }

    public void validateLogin(PasswordEncoder passwordEncoder , String password , String encodedPassword , DeviceType deviceType) throws UnAuthException {
        if ( !passwordEncoder.matches(password , encodedPassword) ) {
            throw new UnAuthException("Invalid password");
        }
        if ( deviceType.equals(DeviceType.INVALID_TYPE) ) {
            throw new UnAuthException("You cannot login to this account as client");
        }
    }

    public void validateLogin(PasswordEncoder passwordEncoder , String password , String encodedPassword) throws UnAuthException {
        if ( !passwordEncoder.matches(password , encodedPassword) ) {
            throw new UnAuthException("Invalid password");
        }
    }

    public void validateDeviceType(DeviceType userDeviceType , DeviceType deviceType) throws ValidationProjectException{
        if(!userDeviceType.equals(deviceType)){
            throw new ValidationProjectException("This account is not linked with given device uuid");
        }
    }
}
