package pl.projectfiveg.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.enums.TaskType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private Long taskId;
    private TaskType taskType;

}
