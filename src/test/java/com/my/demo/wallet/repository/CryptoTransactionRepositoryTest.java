package com.my.demo.wallet.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.demo.wallet.Application;
import com.my.demo.wallet.entity.CryptoCoin;
import com.my.demo.wallet.entity.CryptoTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class CryptoTransactionRepositoryTest {

    @Autowired
    private CryptoTransactionRepository cryptoTransactionRepository;

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
        cryptoTransactionRepository.save(record);
        System.out.println(record);
    }

    @Test
    public void testFindPage() {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
        Example<CryptoTransaction> example = Example.of(CryptoTransaction.builder().isDelete(false).build());
        Page<CryptoTransaction> page = cryptoTransactionRepository.findAll(example, pageable);
        System.out.println(JSONObject.toJSONString(page));
    }

    @Test
    public void testFindList() {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Example<CryptoTransaction> example = Example.of(CryptoTransaction.builder().isDelete(false).build());
        List<CryptoTransaction> transactionList = cryptoTransactionRepository.findAll(example, sort);
        System.out.println(transactionList);
    }

    @Test
    public void testFindById() {
        Optional<CryptoTransaction> optional = cryptoTransactionRepository.findById(1L);
        System.out.println(optional.get());
    }

    @Test
    public void testGetOne() {
        CryptoTransaction transaction = cryptoTransactionRepository.getOne(1L);
        cryptoTransactionRepository.save(CryptoTransaction.builder()
                .id(transaction.getId())
                .coin(CryptoCoin.builder().id(1L).build())
                .build());
    }

    @Test
    public void testCount() {
        long counter = cryptoTransactionRepository.count(Example.of(CryptoTransaction.builder()
                .fromAddress("0x01e02c56248359183Ce361C4BceD3F939183F42D")
                .status(CryptoTransaction.TransactionStatus.PENDING.getStatus())
                .build()));
        System.out.println(counter);
    }
}
