package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceAuthDTO {
    private String token;
    private String uuid;
}
