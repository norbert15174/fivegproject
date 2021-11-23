package pl.projectfiveg.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Device {

    @Id
    private String uuid;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    private String deviceModel;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime connectionDate;
    @Enumerated(EnumType.STRING)
    private CurrentStatus status;
    @OneToMany(mappedBy = "device")
    private Set <Task> tasks = new HashSet <>();

    public boolean updateStatus(Set <TaskDTO> taskToExecute) {
        this.connectionDate = LocalDateTime.now();
        if ( this.status.equals(CurrentStatus.ACTIVE) && !taskToExecute.isEmpty() ) {
            this.status = CurrentStatus.WORKING;
            return true;
        } else if ( this.status.equals(CurrentStatus.WORKING) && taskToExecute.isEmpty() ) {
            this.status = CurrentStatus.ACTIVE;
            return true;
        } else if ( this.status.equals(CurrentStatus.INACTIVE) && !taskToExecute.isEmpty() ) {
            this.status = CurrentStatus.WORKING;
            return true;
        } else if ( this.status.equals(CurrentStatus.INACTIVE) && taskToExecute.isEmpty() ) {
            this.status = CurrentStatus.ACTIVE;
            return true;
        }
        return false;
    }

    public DeviceStatusMessage toDeviceStatusMessage() {
        return new DeviceStatusMessage(this);
    }
}
