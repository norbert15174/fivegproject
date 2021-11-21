package pl.projectfiveg.quartz.executions;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.projectfiveg.models.Device;
import pl.projectfiveg.services.interfaces.IDeviceService;
import pl.projectfiveg.services.query.interfaces.IDeviceQueryService;

import java.util.List;

@Component
@DisallowConcurrentExecution
public class DeviceTaskJob implements Job {

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IDeviceQueryService deviceQueryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List <Device> devices = deviceQueryService.getDevicesStatusChange();
        for (Device device : devices) {
            deviceService.updateDevicesStatus(device);
        }
    }
}
