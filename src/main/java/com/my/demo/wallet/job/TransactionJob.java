package com.my.demo.wallet.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TransactionJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(TransactionJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("==> {} start.", "TransactionJob");

        logger.info("<== {} end.", "TransactionJob");
    }
}
