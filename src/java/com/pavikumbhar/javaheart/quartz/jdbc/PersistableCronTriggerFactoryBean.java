package com.pavikumbhar.javaheart.quartz.jdbc;
 
import java.text.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

 
/**
 *
 * @author pavikumbhar
 */
 
public class PersistableCronTriggerFactoryBean extends CronTriggerFactoryBean {
    @Override
    public void afterPropertiesSet() throws ParseException {
        super.afterPropertiesSet();

        //Remove the JobDetail element
        getJobDataMap().remove(JobService.JOB_DETAIL_KEY);
    }
}

 interface JobService {
    public static final String JOB_DETAIL_KEY = "jobDetail";
  
}