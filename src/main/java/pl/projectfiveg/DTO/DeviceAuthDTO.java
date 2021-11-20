package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceAuthDTO {
    private final String token;
    private final String uuid;
}
