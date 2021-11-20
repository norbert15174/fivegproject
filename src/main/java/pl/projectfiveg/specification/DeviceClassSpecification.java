package pl.projectfiveg.specification;

import com.google.api.client.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.models.enums.SortedType;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.util.Set;

import static org.springframework.data.jpa.domain.Specification.where;

public class DeviceClassSpecification extends BaseSpecification <Device, DeviceSearchCriteria> {
    @Override
    public Specification <Device> getFilter(DeviceSearchCriteria request) {
        return where(uuid(request.getUuid())
                .and(deviceType(request.getDeviceTypes())
                        .and(deviceStatuses(request.getDeviceStatuses())
                                .and(name(request.getName())
                                        .and(order(request.getSortBy()))))));
    }

    private Specification <Device> uuid(String uuid) {
        return (root , query , criteriaBuilder) -> {
            if ( Strings.isNullOrEmpty(uuid) ) {
                return null;
            }
            return criteriaBuilder.like(root.get("uuid") , containsLoweCase(uuid));
        };
    }

    private Specification <Device> deviceType(Set <DeviceType> deviceTypes) {
        return (root , query , criteriaBuilder) -> {
            if ( isCollectionNullOrEmpty(deviceTypes) ) {
                return null;
            }
            return root.get("deviceType").in(deviceTypes);
        };
    }

    private Specification <Device> deviceStatuses(Set <CurrentStatus> deviceStatuses) {
        return (root , query , criteriaBuilder) -> {
            if ( isCollectionNullOrEmpty(deviceStatuses) ) {
                return null;
            }
            return root.get("status").in(deviceStatuses);
        };
    }

    private Specification <Device> name(String name) {
        return (root , query , criteriaBuilder) -> {
            if ( Strings.isNullOrEmpty(name) ) {
                return null;
            }
            return criteriaBuilder.like(root.get("deviceModel") , name);
        };
    }

    private Specification <Device> order(SortedType type) {
        if ( type == null ) {
            return orderByDesc("uuid");
        }
        switch (type) {
            case UUID_DESC:
                return orderByDesc("uuid");
            case UUID_ASC:
                return orderByAsc("uuid");
            case NAME_ASC:
                return orderByAsc("deviceModel");
            case NAME_DESC:
                return orderByDesc("deviceModel");
            default:
                return orderByDesc("uuid");
        }
    }
}


