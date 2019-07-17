package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.entity.UserDepositAddress;
import com.my.demo.wallet.entity.UserDepositRecord;
import com.my.demo.wallet.repository.UserDepositAddressRepository;
import com.my.demo.wallet.repository.UserDepositRecordRepository;
import com.my.demo.wallet.service.UserDepositService;
import com.my.demo.wallet.utils.EthereumUtils;
import com.my.demo.wallet.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserDepositServiceImpl implements UserDepositService {
    private static final Logger logger = LoggerFactory.getLogger(UserDepositServiceImpl.class);

    @Value("${goldbox.deposit.api:http://127.0.0.1:8899/api/temp/manage/deposit/query/symbol/txid}")
    private String depositApi;

    @Autowired
    private UserDepositAddressRepository userDepositAddressRepository;

    @Autowired
    private UserDepositRecordRepository userDepositRecordRepository;

    @Override
    public void handleEthereumDeposit(BigInteger blockNumber) throws Exception {
        List<EthBlock.TransactionResult> transactionResultList = EthereumUtils.getTransactionList(blockNumber);
        transactionResultList.forEach(transactionResult -> {
            Transaction transaction = (Transaction) transactionResult.get();
            String to = transaction.getTo();
            String hash = transaction.getHash();
            long addressCounter = userDepositAddressRepository.count(Example.of(UserDepositAddress.builder().address(to).build()));
            if (addressCounter == 0) {
                return;
            }
            long hashCounter = userDepositRecordRepository.count(Example.of(UserDepositRecord.builder().hash(hash).build()));
            if (hashCounter > 0) {
                return;
            }
            logger.info("Handle transaction, hash : {}", hash);
            HttpUtils.get(depositApi.replace("symbol", "eth").replace("txid", hash));
        });
    }
}
