package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterDTO {
    private final String login;
    private final String password;
}
