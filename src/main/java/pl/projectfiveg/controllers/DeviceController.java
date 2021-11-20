package pl.projectfiveg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projectfiveg.DTO.DeviceDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.services.interfaces.IDeviceService;
import pl.projectfiveg.services.interfaces.ITaskService;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/device")
@CrossOrigin("*")
public class DeviceController {

    private final IDeviceService deviceService;
    private final ITaskService taskService;

    @Autowired
    public DeviceController(IDeviceService deviceService , ITaskService taskService) {
        this.deviceService = deviceService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity <List <DeviceDTO>> getDevices(Principal principal , DeviceSearchCriteria deviceSearchCriteria) {
        return deviceService.getDevices(deviceSearchCriteria);
    }

    @GetMapping("/tasks")
    public ResponseEntity <Set <TaskDTO>> getTasks(Principal principal , @RequestHeader(name = "device-uuid") String uuid) {
        return taskService.getTasksToExecute(principal , uuid);
    }

}
