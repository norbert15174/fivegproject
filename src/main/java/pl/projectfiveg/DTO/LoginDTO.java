package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDTO {
    private String login;
    private String password;
}
