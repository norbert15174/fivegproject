package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.projectfiveg.models.enums.Role;

@AllArgsConstructor
@Getter
public class UserDTO {
    private final String login;
    private final String role;
    private final String token;
}
