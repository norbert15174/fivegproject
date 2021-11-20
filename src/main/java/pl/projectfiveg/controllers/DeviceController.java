package pl.projectfiveg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projectfiveg.DTO.DeviceDTO;
import pl.projectfiveg.services.interfaces.IDeviceService;
import pl.projectfiveg.specification.criteria.DeviceSearchCriteria;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/device")
@CrossOrigin("*")
public class DeviceController {

    private final IDeviceService deviceService;

    @Autowired
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity <List <DeviceDTO>> getDevices(Principal principal, DeviceSearchCriteria deviceSearchCriteria) {
        return deviceService.getDevices(deviceSearchCriteria);
    }

}
