package com.my.demo.wallet.service;

import java.math.BigInteger;

public interface UserDepositService {
    void handleEthereumDeposit(BigInteger blockNumber) throws Exception;
}
