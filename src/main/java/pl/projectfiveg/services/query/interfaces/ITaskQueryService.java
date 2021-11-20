package pl.projectfiveg.services.query.interfaces;

import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.util.List;
import java.util.Set;

public interface ITaskQueryService {
    List <Task> getTasks(TaskSearchCriteria taskSearchCriteria , Long id);
    Set <TaskDTO> getTasksByDeviceUuid(String deviceUuid);
}
