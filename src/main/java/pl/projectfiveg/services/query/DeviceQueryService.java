package pl.projectfiveg.services.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.projectfiveg.exceptions.DeviceNotFoundException;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.repositories.IDeviceRepository;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.specification.DeviceClassSpecification;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryService implements IDeviceQueryService {

    private final IDeviceRepository deviceRepository;

    @Autowired
    public DeviceQueryService(IDeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device getDeviceByUuid(String uuid) throws DeviceNotFoundException {
        return deviceRepository.getByIdOpt(uuid).orElseThrow(() -> new DeviceNotFoundException("Device doesnt exist in system"));
    }

    @Override
    public Optional <Device> getDeviceByUuidOpt(String uuid) {
        return deviceRepository.getByIdOpt(uuid);
    }

    @Override
    public List <Device> getDevices(DeviceSearchCriteria deviceSearchCriteria) {
        Specification<Device> filter = new DeviceClassSpecification().getFilter(deviceSearchCriteria);
        return deviceRepository.findAll(filter);
    }

    @Override
    public List <Device> getDevicesStatusChange() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(10);
        return deviceRepository.findDevicesStatusChange(time);
    }
}
