package com.my.demo.wallet.service;

import com.my.demo.wallet.Application;
import com.my.demo.wallet.entity.CryptoCoin;
import com.my.demo.wallet.entity.CryptoTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class CryptoTransactionServiceTest {
    @Autowired
    private CryptoTransactionService cryptoTransactionService;

    @Test
    public void testFindAll() {
        List<CryptoTransaction> transactionList = cryptoTransactionService.findList(CryptoTransaction.builder().isDelete(false).build());
        System.out.println(transactionList);
    }

    @Test
    public void testSave() {
        CryptoTransaction record = CryptoTransaction.builder()
                .fromAddress("0x01e02c56248359183Ce361C4BceD3F939183F42D")
                .toAddress("0x9dB0976d0203503fd3E03BFc1C4E23e5fECd0094")
                .amount(BigDecimal.ONE)
                .remarks("test")
                .type(CryptoTransaction.TransactionType.WITHDRAWAL.getType())
                .status(CryptoTransaction.TransactionStatus.NORMAL.getStatus())
                .createTime(new Date())
                .modifyTime(new Date())
                .isDelete(false)
                .build();
        System.out.println(record);
        CryptoTransaction result = cryptoTransactionService.save(record);
        System.out.println(result);
    }

    @Test
    public void testCount() {
        long counter = cryptoTransactionService.count(CryptoTransaction.builder()
                .fromAddress("")
                .status(CryptoTransaction.TransactionStatus.PENDING.getStatus())
                .build());
    }
}
