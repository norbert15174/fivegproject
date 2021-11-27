package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterDTO {
    private String login;
    private String password;
}
