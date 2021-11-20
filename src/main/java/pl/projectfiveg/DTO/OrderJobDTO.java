package pl.projectfiveg.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.enums.TaskType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderJobDTO {

    private String uuid;
    private TaskType type;

}
