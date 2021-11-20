package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.projectfiveg.models.enums.DeviceType;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class DeviceLoginDTO {
    @NotNull
    private final String login;
    @NotNull
    private final String password;
    private final String uuid;
    private final String name;
}
