package pl.projectfiveg.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.DTO.TaskDTO;

import java.security.Principal;

public interface ITaskService {
    ResponseEntity <TaskDTO> create(Principal principal , OrderJobDTO job);
}
