package pl.projectfiveg.services.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projectfiveg.repositories.ITaskRepository;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;

@Service
public class TaskQueryService implements ITaskQueryService {

    private final ITaskRepository taskRepository;

    @Autowired
    public TaskQueryService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
