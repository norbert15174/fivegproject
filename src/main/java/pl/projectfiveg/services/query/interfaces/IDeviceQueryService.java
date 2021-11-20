package pl.projectfiveg.services.query.interfaces;

import pl.projectfiveg.exceptions.DeviceNotFoundException;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface IDeviceQueryService {

    Device getDeviceByUuid(String uuid) throws DeviceNotFoundException;
    Optional <Device> getDeviceByUuidOpt(String uuid);

    List <Device> getDevices(DeviceSearchCriteria deviceSearchCriteria);
}
