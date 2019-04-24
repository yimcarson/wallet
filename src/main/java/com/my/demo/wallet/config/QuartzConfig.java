package com.my.demo.wallet.config;

import com.my.demo.wallet.job.TransactionJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail transactionJobDeail() {
        return JobBuilder.newJob(TransactionJob.class).withIdentity("transactionJob").storeDurably().build();
    }

    @Bean
    public Trigger transactionJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(transactionJobDeail())
                .withIdentity("transactionJob")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
