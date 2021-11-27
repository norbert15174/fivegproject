package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.projectfiveg.models.enums.Role;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String login;
    private String role;
    private String token;
}
