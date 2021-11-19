package pl.projectfiveg.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.exceptions.ValidationProjectException;
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

}
