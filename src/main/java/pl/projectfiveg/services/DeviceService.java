package pl.projectfiveg.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.DeviceDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.services.interfaces.IDeviceService;
import pl.projectfiveg.services.interfaces.IUserService;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.services.update.interfaces.IDeviceUpdateService;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;
import pl.projectfiveg.validators.DeviceValidator;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceService implements IDeviceService {
    private final IDeviceQueryService deviceQueryService;
    private final IDeviceUpdateService deviceUpdateService;
    private final IUserService userService;
    private final DeviceValidator deviceValidator;
    private final ITaskQueryService taskQueryService;

    public DeviceService(IDeviceQueryService deviceQueryService , IDeviceUpdateService deviceUpdateService , IUserService userService , DeviceValidator deviceValidator , ITaskQueryService taskQueryService) {
        this.deviceQueryService = deviceQueryService;
        this.deviceUpdateService = deviceUpdateService;
        this.userService = userService;
        this.deviceValidator = deviceValidator;
        this.taskQueryService = taskQueryService;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity <List <DeviceDTO>> getDevices(DeviceSearchCriteria deviceSearchCriteria) {
        return new ResponseEntity(deviceQueryService.getDevices(deviceSearchCriteria).stream().map(DeviceDTO::new).collect(Collectors.toList()) , HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity <DeviceDTO> deviceStillConnected(Principal principal , String uuid) {
        User user = userService.getUserByLogin(principal.getName());
        Device device;
        Set <TaskDTO> tasks;
        try {
            device = deviceQueryService.getDeviceByUuid(uuid);
            deviceValidator.validateConnected(user , device);
            tasks = taskQueryService.getTasksByDeviceUuid(uuid);
        } catch ( Exception ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        device.updateStatus(tasks);
        deviceUpdateService.update(device);
        return new ResponseEntity(new DeviceDTO(device) , HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity <DeviceDTO> deviceDisconnect(Principal principal , String uuid) {
        User user = userService.getUserByLogin(principal.getName());
        Device device;
        try {
            device = deviceQueryService.getDeviceByUuid(uuid);
            deviceValidator.validateDisconnect(user , device);
        } catch ( Exception ex ) {
            return new ResponseEntity(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        device.setStatus(CurrentStatus.INACTIVE);
        deviceUpdateService.update(device);
        return new ResponseEntity(new DeviceDTO(device) , HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateDevicesStatus(Device device) {
        device.setStatus(CurrentStatus.INACTIVE);
        deviceUpdateService.update(device);
    }

}
