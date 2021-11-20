package pl.projectfiveg.validators;

import org.springframework.stereotype.Component;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.exceptions.ValidationProjectException;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;

@Component
public class TaskValidator {

    public void validateTask(Device device , OrderJobDTO job , User user) {
        if ( !device.getStatus().equals(CurrentStatus.ACTIVE) ) {
            throw new ValidationProjectException("Device is inactive or working, therefore you cannot execute this task");
        }
        if ( !user.getDeviceType().equals(DeviceType.INVALID_TYPE) ) {
            throw new ValidationProjectException("You cannot order task as device");
        }
    }

    public void validateDeviceTask(Device device , User user) {
        if ( user.getDeviceType().equals(DeviceType.INVALID_TYPE) ) {
            throw new ValidationProjectException("You cannot get this task as WEB_CLIENT");
        }
        if ( !device.getDeviceType().equals(user.getDeviceType()) ) {
            throw new ValidationProjectException("If you want to get device '" + device.getUuid() + "' tasks, you have to log in as device user: " + device.getDeviceType());
        }
    }
}
