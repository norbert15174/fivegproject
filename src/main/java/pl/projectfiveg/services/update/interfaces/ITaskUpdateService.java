package pl.projectfiveg.services.update.interfaces;

import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.User;

public interface ITaskUpdateService {
    Task createTask(OrderJobDTO job , Device device , User user);
}
