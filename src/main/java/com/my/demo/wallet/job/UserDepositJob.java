package com.my.demo.wallet.job;

import com.my.demo.wallet.cache.RedisClientTemplate;
import com.my.demo.wallet.service.UserDepositService;
import com.my.demo.wallet.utils.EthereumUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.math.BigInteger;

public class UserDepositJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(UserDepositJob.class);

    @Autowired
    private UserDepositService userDepositService;

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("==> {} begin.", "UserDepositJob");
        try {
            BigInteger ethBlockIndex = BigInteger.ZERO;
            String ethBlockIndexStr = redisClientTemplate.hget("block_index", "eth");
            if (StringUtils.isBlank(ethBlockIndexStr)) {
                redisClientTemplate.hset("block_index", "eth", "0");
            } else {
                ethBlockIndex = new BigInteger(ethBlockIndexStr);
            }
            logger.info("block_index : {}", ethBlockIndex);
            BigInteger confirm = BigInteger.TEN;
            BigInteger blockNumber = EthereumUtils.getBlockNumber();
            if (ethBlockIndex.add(confirm).compareTo(blockNumber) == -1) {
                userDepositService.handleEthereumDeposit(blockNumber);
                redisClientTemplate.hset("block_index", "eth", ethBlockIndex.add(BigInteger.ONE).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("<== {} over.", "UserDepositJob");
    }
}
