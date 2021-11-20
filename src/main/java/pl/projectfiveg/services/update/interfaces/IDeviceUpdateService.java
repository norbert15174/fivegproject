package pl.projectfiveg.services.update.interfaces;

import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.DeviceType;

public interface IDeviceUpdateService {
    void update(Device device);

    Device createDevice(DeviceType deviceType , String name);
}
