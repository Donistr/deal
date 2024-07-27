package org.example.deal.quartz.config;

import org.example.deal.quartz.job.SetMainBorrowerJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Value("${contractor.resend-set-main-borrower-message-on-fail-timeout-cron-expression}")
    private String setMainBorrowerTimeout;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SetMainBorrowerJob.class)
                .withIdentity("setMainBorrowerJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("setMainBorrowerTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(setMainBorrowerTimeout))
                .build();
    }

}
