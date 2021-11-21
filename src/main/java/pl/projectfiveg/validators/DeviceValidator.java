package pl.projectfiveg.validators;

import org.springframework.stereotype.Component;
import pl.projectfiveg.exceptions.ValidationProjectException;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.User;

@Component
public class DeviceValidator {
    public void validateConnected(User user , Device device) {
        if(!user.getDeviceType().equals(device.getDeviceType())){
            throw new ValidationProjectException("You cannot notify that a device is still active until you are logged in as" + device.getDeviceType() + " account");
        }
    }

    public void validateDisconnect(User user , Device device) {
        if(!user.getDeviceType().equals(device.getDeviceType())){
            throw new ValidationProjectException("You cannot notify disconnect until you are logged in as" + device.getDeviceType() + " account");
        }
    }
}
