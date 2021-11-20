package pl.projectfiveg.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.projectfiveg.DTO.DeviceLoginDTO;
import pl.projectfiveg.DTO.LoginDTO;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.DTO.UserDTO;

public interface IUserServiceInterface {
    ResponseEntity <UserDTO> register(RegisterDTO register);

    ResponseEntity <UserDTO> login(LoginDTO login);

    ResponseEntity <UserDTO> deviceLogin(DeviceLoginDTO login);

}
