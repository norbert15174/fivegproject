package pl.projectfiveg.services.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.repositories.ITaskRepository;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.specification.TaskClassSpecification;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.util.List;
import java.util.Set;

@Service
public class TaskQueryService implements ITaskQueryService {

    private final ITaskRepository taskRepository;

    @Autowired
    public TaskQueryService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List <Task> getTasks(TaskSearchCriteria taskSearchCriteria , Long id) {
        Specification <Task> filter = new TaskClassSpecification().getFilter(taskSearchCriteria , id);
        return taskRepository.findAll(filter);
    }

    @Transactional(readOnly = true)
    @Override
    public Set <TaskDTO> getTasksByDeviceUuid(String deviceUuid) {
        return taskRepository.findTasksByDeviceUuid(deviceUuid);
    }
}
