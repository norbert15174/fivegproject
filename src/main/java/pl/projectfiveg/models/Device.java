package pl.projectfiveg.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

}
