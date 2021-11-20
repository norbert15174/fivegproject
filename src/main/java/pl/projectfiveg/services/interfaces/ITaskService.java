package pl.projectfiveg.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface ITaskService {
    ResponseEntity <TaskDTO> create(Principal principal , OrderJobDTO job);

    ResponseEntity<List<TaskDTO>> getTasks(Principal principal , TaskSearchCriteria taskSearchCriteria);

    ResponseEntity <Set <TaskDTO>> getTasksToExecute(Principal principal , String deviceUuid);
}
