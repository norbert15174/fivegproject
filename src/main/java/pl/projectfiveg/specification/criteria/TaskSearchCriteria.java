package pl.projectfiveg.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.models.enums.TaskStatus;
import pl.projectfiveg.models.enums.TaskType;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaskSearchCriteria {
    private Set <TaskType> taskTypes;
    private String deviceUuid;
    private String deviceName;
    private Set<TaskStatus> taskStatuses;
    private Set<DeviceType> deviceTypes;
}
