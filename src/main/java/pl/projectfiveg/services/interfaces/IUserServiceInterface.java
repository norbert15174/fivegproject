package pl.projectfiveg.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.DTO.UserDTO;

public interface IUserServiceInterface {
    ResponseEntity <UserDTO> register(RegisterDTO register);
}
