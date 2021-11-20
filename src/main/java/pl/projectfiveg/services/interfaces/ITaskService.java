package pl.projectfiveg.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.File;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface ITaskService {
    ResponseEntity <TaskDTO> create(Principal principal , OrderJobDTO job);

    ResponseEntity<List<TaskDTO>> getTasks(Principal principal , TaskSearchCriteria taskSearchCriteria);

    ResponseEntity <Set <TaskDTO>> getTasksToExecute(Principal principal , String deviceUuid);

    ResponseEntity<TaskDTO> uploadFile(Principal principal , String uuid , Long taskId , MultipartFile file);

    ResponseEntity<Resource> getFile(Principal principal , Long taskId);
}
