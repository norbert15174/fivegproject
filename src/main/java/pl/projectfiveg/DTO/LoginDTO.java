package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDTO {
    private final String login;
    private final String password;
}
