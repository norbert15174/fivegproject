package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.projectfiveg.models.enums.Role;

@AllArgsConstructor
@Getter
public class UserDTO {
    private String login;
    private String role;
    private String token;
}
