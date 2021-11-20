package pl.projectfiveg.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.projectfiveg.DTO.DeviceDTO;
import pl.projectfiveg.models.User;
import pl.projectfiveg.services.interfaces.IDeviceService;
import pl.projectfiveg.services.interfaces.IUserService;
import pl.projectfiveg.services.interfaces.IUserServiceInterface;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.services.update.interfaces.IDeviceUpdateService;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService implements IDeviceService {
    private final IDeviceQueryService deviceQueryService;
    private final IDeviceUpdateService deviceUpdateService;
    private final IUserService userService;

    public DeviceService(IDeviceQueryService deviceQueryService , IDeviceUpdateService deviceUpdateService , IUserService userService) {
        this.deviceQueryService = deviceQueryService;
        this.deviceUpdateService = deviceUpdateService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity <List <DeviceDTO>> getDevices(DeviceSearchCriteria deviceSearchCriteria) {
        return new ResponseEntity(deviceQueryService.getDevices(deviceSearchCriteria).stream().map(DeviceDTO::new).collect(Collectors.toList()) , HttpStatus.OK);
    }
}
