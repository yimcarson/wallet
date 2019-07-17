package com.my.demo.wallet.service;

import com.my.demo.wallet.entity.CryptoTransaction;

import java.math.BigInteger;
import java.util.List;

public interface CryptoTransactionService {
    List<CryptoTransaction> findList(CryptoTransaction record);

    CryptoTransaction save(CryptoTransaction record);

    void handleWithdraw();

    long count(CryptoTransaction record);

    void fetch(BigInteger blockNumber);
}
