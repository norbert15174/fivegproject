package pl.projectfiveg.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import pl.projectfiveg.quartz.executions.DeviceTaskJob;

@Configuration
public class QuartzSubmitJobs {

    @Bean
    public JobDetailFactoryBean deviceTaskExecution() {
        return SchedulerJobCreator.createJob(DeviceTaskJob.class , "deviceTask");
    }

    @Bean
    public CronTriggerFactoryBean deviceTaskExecutionTrigger(@Qualifier("deviceTaskExecution") JobDetail jobDetail) {
        return SchedulerJobCreator.createCronTrigger(jobDetail , "deviceTaskExecutionTrigger" , "0 */10 * ? * *");
    }

}
