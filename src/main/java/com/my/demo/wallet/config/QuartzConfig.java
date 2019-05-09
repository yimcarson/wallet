package com.my.demo.wallet.config;

import com.my.demo.wallet.job.WithdrawalJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail withdrawalJobDeail() {
        return JobBuilder.newJob(WithdrawalJob.class).withIdentity("withdrawalJob").storeDurably().build();
    }

    @Bean
    public Trigger withdrawalJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(withdrawalJobDeail())
                .withIdentity("withdrawalJob")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
