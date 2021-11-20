package pl.projectfiveg.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.User;
import pl.projectfiveg.models.enums.TaskStatus;
import pl.projectfiveg.models.enums.TaskType;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    private Long id;
    private TaskType taskType;
    private TaskStatus status;
    private String fileUrl;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderStart;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderEnd;
    private DeviceGetDTO deviceGetDTO;

    public TaskDTO(Task task, String fileUrl){
        this.id = task.getId();
        this.taskType = task.getTaskType();
        this.status = task.getStatus();
        this.fileUrl = fileUrl;
        this.orderStart = task.getOrderStart();
        this.orderEnd = task.getOrderEnd();
        this.deviceGetDTO = new DeviceGetDTO(task.getDevice());
    }
}
