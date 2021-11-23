package pl.projectfiveg.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeviceStatusMessage {
    private String uuid;
    private DeviceType deviceType;
    private String deviceModel;
    private CurrentStatus status;

    public DeviceStatusMessage(Device device) {
        this.uuid = device.getUuid();
        this.deviceModel = device.getDeviceModel();
        this.deviceType = device.getDeviceType();
        this.status = device.getStatus();
    }
}
