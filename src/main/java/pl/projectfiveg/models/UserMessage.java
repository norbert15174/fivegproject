package pl.projectfiveg.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.enums.TaskStatus;
import pl.projectfiveg.models.enums.TaskType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    private Long taskId;
    private TaskType taskType;
    private TaskStatus status = TaskStatus.FINISHED;

    public UserMessage(Long taskId , TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
    }

}
