package com.my.demo.wallet.service;

import com.my.demo.wallet.entity.CryptoCoin;
import com.my.demo.wallet.entity.CryptoTransaction;

import java.util.List;

public interface CryptoTransactionService {
    List<CryptoTransaction> findList(CryptoTransaction record);

    CryptoTransaction save(CryptoTransaction record);
}
