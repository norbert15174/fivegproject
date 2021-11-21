package pl.projectfiveg.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Slf4j
public class SchedulerJobCreator {

    public static JobDetailFactoryBean createJob(Class <? extends Job> jobClass , String jobName) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setName(jobName);
        factoryBean.setDurability(true);

        return factoryBean;
    }

    public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail , String triggerName , String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

        return factoryBean;
    }

}
