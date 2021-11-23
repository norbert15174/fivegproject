package pl.projectfiveg.services.update;

import com.google.api.client.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.creators.DeviceCreator;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.repositories.IDeviceRepository;
import pl.projectfiveg.services.WebSocketClientService;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.services.update.interfaces.IDeviceUpdateService;

import java.util.Set;
import java.util.UUID;

@Service
public class DeviceUpdateService implements IDeviceUpdateService {

    private final IDeviceRepository deviceRepository;
    private final IDeviceQueryService deviceQueryService;
    private final ITaskQueryService taskQueryService;
    private final WebSocketClientService webSocketClientService;

    @Autowired
    public DeviceUpdateService(IDeviceRepository deviceRepository , IDeviceQueryService deviceQueryService , ITaskQueryService taskQueryService , WebSocketClientService webSocketClientService) {
        this.deviceRepository = deviceRepository;
        this.deviceQueryService = deviceQueryService;
        this.taskQueryService = taskQueryService;
        this.webSocketClientService = webSocketClientService;
    }

    @Transactional
    @Override
    public void update(Device device) {
        deviceRepository.save(device);
    }

    @Transactional
    @Override
    public Device createDevice(DeviceType deviceType , String name) {
        return create(DeviceCreator.createDevice(deviceType , name , generateUuid("")));
    }

    @Override
    public void updateStatusIfNeeded(String uuid) {
        Device device = deviceQueryService.getDeviceByUuid(uuid);
        Set <Task> tasks = taskQueryService.getNotFinishedTasksByDeviceUuid(uuid);
        if ( tasks.isEmpty() && !device.getStatus().equals(CurrentStatus.ACTIVE) ) {
            device.setStatus(CurrentStatus.ACTIVE);
            update(device);
            try {
                webSocketClientService.webSocketGlobal(device.toDeviceStatusMessage());
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    private Device create(Device device) {
        return deviceRepository.save(device);
    }

    private String generateUuid(String uuid) {
        if ( Strings.isNullOrEmpty(uuid) ) {
            UUID newUuid = UUID.randomUUID();
            if ( deviceQueryService.getDeviceByUuidOpt(uuid).isPresent() ) {
                return generateUuid("");
            } else {
                return newUuid.toString();
            }
        }
        return uuid;
    }
}
