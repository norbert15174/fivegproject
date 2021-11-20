package pl.projectfiveg.creators;

import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;

import java.time.LocalDateTime;

public class DeviceCreator {

    public static Device createDevice(DeviceType deviceType , String name, String uuid){
        Device device = new Device();
        device.setStatus(CurrentStatus.ACTIVE);
        device.setConnectionDate(LocalDateTime.now());
        device.setDeviceModel(name);
        device.setDeviceType(deviceType);
        device.setUuid(uuid);
        return device;
    }

}
