package pl.projectfiveg.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.projectfiveg.DTO.DeviceDTO;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.security.Principal;
import java.util.List;

public interface IDeviceService {
    ResponseEntity<List<DeviceDTO>> getDevices(DeviceSearchCriteria deviceSearchCriteria);

    ResponseEntity <DeviceDTO> deviceStillConnected(Principal principal , String uuid);

    ResponseEntity <DeviceDTO> deviceDisconnect(Principal principal , String uuid);

    void updateDevicesStatus(Device device);
}
