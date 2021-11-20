package pl.projectfiveg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.services.interfaces.ITaskService;
import pl.projectfiveg.services.interfaces.IUserService;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;
import pl.projectfiveg.services.query.interfaces.ITaskQueryService;
import pl.projectfiveg.services.update.interfaces.IDeviceUpdateService;
import pl.projectfiveg.services.update.interfaces.ITaskUpdateService;
import pl.projectfiveg.validators.TaskValidator;

import java.security.Principal;

@Service
public class TaskService implements ITaskService {

    private final ITaskUpdateService taskUpdateService;
    private final ITaskQueryService taskQueryService;
    private final IDeviceUpdateService deviceUpdateService;
    private final IUserService userService;
    private final IDeviceQueryService deviceQueryService;
    private final TaskValidator taskValidator;

    @Autowired
    public TaskService(ITaskUpdateService taskUpdateService , ITaskQueryService taskQueryService , IDeviceUpdateService deviceUpdateService , IUserService userService , IDeviceQueryService deviceQueryService , TaskValidator taskValidator) {
        this.taskUpdateService = taskUpdateService;
        this.taskQueryService = taskQueryService;
        this.deviceUpdateService = deviceUpdateService;
        this.userService = userService;
        this.deviceQueryService = deviceQueryService;
        this.taskValidator = taskValidator;
    }

    @Transactional
    @Override
    public ResponseEntity <TaskDTO> create(Principal principal , OrderJobDTO job) {
        User user;
        Device device;
        try {
            user = userService.getUserByLogin(principal.getName());
            device = deviceQueryService.getDeviceByUuid(job.getUuid());
            taskValidator.validateTask(device , job, user);
        } catch ( Exception e ) {
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
        device.setStatus(CurrentStatus.WORKING);
        Task task = taskUpdateService.createTask(job , device , user);
        deviceUpdateService.update(device);
        return new ResponseEntity <>(new TaskDTO(task , null) , HttpStatus.OK);
    }

}
