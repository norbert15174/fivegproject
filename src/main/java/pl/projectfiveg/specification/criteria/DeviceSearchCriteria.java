package pl.projectfiveg.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.models.enums.SortedType;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSearchCriteria {
    private String uuid;
    private Set <DeviceType> deviceTypes;
    private Set<CurrentStatus> deviceStatuses;
    private String name;
    private SortedType sortBy = SortedType.UUID_DESC;
}
