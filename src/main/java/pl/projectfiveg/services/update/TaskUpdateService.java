package pl.projectfiveg.services.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.creators.TaskCreator;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.User;
import pl.projectfiveg.repositories.ITaskRepository;
import pl.projectfiveg.services.update.interfaces.ITaskUpdateService;

@Service
public class TaskUpdateService implements ITaskUpdateService {

    private final ITaskRepository taskRepository;

    @Autowired
    public TaskUpdateService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    @Override
    public Task createTask(OrderJobDTO job , Device device , User user) {
        return create(TaskCreator.createTask(job , device , user));
    }

    private Task create(Task task) {
        return taskRepository.save(task);
    }
}
