package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterDTO {
    private String login;
    private String password;
}
