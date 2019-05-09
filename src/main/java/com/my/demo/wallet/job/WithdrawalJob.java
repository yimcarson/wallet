package com.my.demo.wallet.job;

import com.my.demo.wallet.entity.CryptoTransaction;
import com.my.demo.wallet.service.CryptoTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class WithdrawalJob extends QuartzJobBean {
    @Autowired
    private CryptoTransactionService cryptoTransactionService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{} start.", this.getClass().getName());
        cryptoTransactionService.handleWithdraw();
        log.info("{} end.", this.getClass().getName());
    }
}
