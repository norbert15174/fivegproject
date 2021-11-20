package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.DeviceType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeviceGetDTO {
    private String uuid;
    private DeviceType deviceType;
    private String deviceModel;

    public DeviceGetDTO(Device device) {
        this.uuid = device.getUuid();
        this.deviceType = device.getDeviceType();
        this.deviceModel = device.getDeviceModel();
    }
}
