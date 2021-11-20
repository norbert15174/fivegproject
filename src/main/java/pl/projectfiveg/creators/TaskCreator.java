package pl.projectfiveg.creators;

import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskCreator {

    public static Task createTask(OrderJobDTO job , Device device , User user) {
        Task task = new Task();
        task.setStatus(TaskStatus.NEW);
        task.setOrderStart(LocalDateTime.now());
        task.setDevice(device);
        task.setUser(user);
        task.setTaskType(job.getType());
        return task;
    }

}
