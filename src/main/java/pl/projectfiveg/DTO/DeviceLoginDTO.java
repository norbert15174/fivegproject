package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.projectfiveg.models.enums.DeviceType;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class DeviceLoginDTO {
    @NotNull
    private String login;
    @NotNull
    private String password;
    private String uuid;
    private String name;
}
