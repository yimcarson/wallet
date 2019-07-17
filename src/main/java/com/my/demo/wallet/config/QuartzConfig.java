package com.my.demo.wallet.config;

import com.my.demo.wallet.job.UserDepositJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail withdrawalJobDeail() {
        return JobBuilder.newJob(UserDepositJob.class).withIdentity("userDepositJob").storeDurably().build();
    }

    @Bean
    public Trigger withdrawalJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(withdrawalJobDeail())
                .withIdentity("userDepositJob")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
