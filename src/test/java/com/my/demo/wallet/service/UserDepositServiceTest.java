package com.my.demo.wallet.service;

import com.my.demo.wallet.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class UserDepositServiceTest {

    @Autowired
    private UserDepositService userDepositService;

    @Test
    public void testHandleEthereumDeposit() {
        try {
            userDepositService.handleEthereumDeposit(BigInteger.valueOf(6002264L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}