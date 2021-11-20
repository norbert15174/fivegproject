package pl.projectfiveg.specification;

import com.google.api.client.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.projectfiveg.models.Task;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.models.enums.TaskStatus;
import pl.projectfiveg.models.enums.TaskType;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.util.Set;

import static org.springframework.data.jpa.domain.Specification.where;

public class TaskClassSpecification extends BaseSpecification <Task, TaskSearchCriteria> {
    @Override
    public Specification getFilter(TaskSearchCriteria request) {
        return where(uuid(request.getDeviceUuid())
                .and(name(request.getDeviceName()))
                .and(taskStatus(request.getTaskStatuses()))
                .and(taskType(request.getTaskTypes()))
                .and(deviceType(request.getDeviceTypes()))
                .and(orderByAsc("orderStart")));
    }

    public Specification getFilter(TaskSearchCriteria request , Long userId) {
        return where(uuid(request.getDeviceUuid())
                .and(name(request.getDeviceName()))
                .and(taskStatus(request.getTaskStatuses()))
                .and(taskType(request.getTaskTypes()))
                .and(deviceType(request.getDeviceTypes()))
                .and(orderByAsc("orderStart")));
    }

    private Specification <Task> user(Long userId) {
        return (root , query , criteriaBuilder) -> {
            return root.join("user").get("id").in(userId);
        };
    }

    private Specification <Task> uuid(String uuid) {
        return (root , query , criteriaBuilder) -> {
            if ( Strings.isNullOrEmpty(uuid) ) {
                return null;
            }
            return criteriaBuilder.like(root.join("device").get("uuid") , containsLoweCase(uuid));
        };
    }

    private Specification <Task> name(String name) {
        return (root , query , criteriaBuilder) -> {
            if ( Strings.isNullOrEmpty(name) ) {
                return null;
            }
            return criteriaBuilder.like(root.join("device").get("deviceModel") , containsLoweCase(name));
        };
    }

    private Specification <Task> taskType(Set <TaskType> taskTypes) {
        return (root , query , criteriaBuilder) -> {
            if ( isCollectionNullOrEmpty(taskTypes) ) {
                return null;
            }
            return root.get("taskType").in(taskTypes);
        };
    }

    private Specification <Task> taskStatus(Set <TaskStatus> taskStatuses) {
        return (root , query , criteriaBuilder) -> {
            if ( isCollectionNullOrEmpty(taskStatuses) ) {
                return null;
            }
            return root.get("status").in(taskStatuses);
        };
    }

    private Specification <Task> deviceType(Set <DeviceType> deviceTypes) {
        return (root , query , criteriaBuilder) -> {
            if ( isCollectionNullOrEmpty(deviceTypes) ) {
                return null;
            }
            return root.join("device").get("deviceType").in(deviceTypes);
        };
    }

}
