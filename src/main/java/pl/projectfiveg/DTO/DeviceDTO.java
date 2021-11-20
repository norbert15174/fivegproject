package pl.projectfiveg.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.models.enums.CurrentStatus;
import pl.projectfiveg.models.enums.DeviceType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeviceDTO {

    private String uuid;
    private DeviceType deviceType;
    private String deviceModel;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastConnection;
    private CurrentStatus status;

    public DeviceDTO(Device device) {
        this.uuid = device.getUuid();
        this.deviceType = device.getDeviceType();
        this.deviceModel = device.getDeviceModel();
        this.lastConnection = device.getConnectionDate();
        this.status = device.getStatus();
    }

}
