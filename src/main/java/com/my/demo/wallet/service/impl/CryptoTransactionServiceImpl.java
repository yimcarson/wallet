package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.entity.CryptoCoin;
import com.my.demo.wallet.entity.CryptoTransaction;
import com.my.demo.wallet.repository.CryptoTransactionRepository;
import com.my.demo.wallet.service.CryptoTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CryptoTransactionServiceImpl implements CryptoTransactionService {

    @Autowired
    private CryptoTransactionRepository cryptoTransactionRepository;

    @Override
    public List<CryptoTransaction> findList(CryptoTransaction record) {
        return cryptoTransactionRepository.findAll(Example.of(record));
    }

    @Override
    public CryptoTransaction save(CryptoTransaction record) {
        log.info("save : {}", record);
        return cryptoTransactionRepository.save(record);
    }
}
