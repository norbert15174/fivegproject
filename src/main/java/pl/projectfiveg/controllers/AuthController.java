package pl.projectfiveg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projectfiveg.DTO.RegisterDTO;
import pl.projectfiveg.DTO.UserDTO;
import pl.projectfiveg.services.interfaces.IUserServiceInterface;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserServiceInterface userService;

    @Autowired
    public AuthController(IUserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity <UserDTO> register(@RequestBody RegisterDTO register) {
        return userService.register(register);
    }

}
