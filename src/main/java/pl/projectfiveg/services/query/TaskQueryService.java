package pl.projectfiveg.services.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.exceptions.TaskNotFoundException;
import pl.projectfiveg.models.File;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.enums.TaskStatus;
import pl.projectfiveg.repositories.IFileRepository;
import pl.projectfiveg.repositories.ITaskRepository;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.specification.TaskClassSpecification;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskQueryService implements ITaskQueryService {

    private final ITaskRepository taskRepository;
    private final IFileRepository fileRepository;

    @Autowired
    public TaskQueryService(ITaskRepository taskRepository , IFileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List <Task> getTasks(TaskSearchCriteria taskSearchCriteria , Long id) {
        Specification <Task> filter = new TaskClassSpecification().getFilter(taskSearchCriteria , id);
        return taskRepository.findAll(filter);
    }

    @Transactional
    @Override
    public Set <TaskDTO> getTasksByDeviceUuid(String deviceUuid) {
        Set <Task> tasks = taskRepository.findFullTasksByDeviceUuid(deviceUuid);
        Set <Task> tasksToUpdate = new HashSet <>();
        for (Task task : tasks) {
            if ( task.getStatus().equals(TaskStatus.NEW) ) {
                task.setStatus(TaskStatus.IN_PROGRESS);
                tasksToUpdate.add(task);
            }
        }
        if ( !tasksToUpdate.isEmpty() ) {
            taskRepository.saveAll(tasks);
        }
        return taskRepository.findTasksByDeviceUuid(deviceUuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task with given ID does not exist"));
    }

    @Override
    public File getFileByTaskId(Long taskId) {
        return fileRepository.findFileByTaskId(taskId).orElseThrow(() -> new TaskNotFoundException("File does not exist"));
    }
}
