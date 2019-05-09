package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.entity.CryptoAddress;
import com.my.demo.wallet.entity.CryptoCoin;
import com.my.demo.wallet.entity.CryptoTransaction;
import com.my.demo.wallet.exception.BalanceNotEnoughException;
import com.my.demo.wallet.exception.UnsupportedException;
import com.my.demo.wallet.repository.CryptoTransactionRepository;
import com.my.demo.wallet.service.CryptoAddressService;
import com.my.demo.wallet.service.CryptoCoinService;
import com.my.demo.wallet.service.CryptoTransactionService;
import com.my.demo.wallet.util.EthereumUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CryptoTransactionServiceImpl implements CryptoTransactionService {
    @Autowired
    private CryptoCoinService cryptoCoinService;

    @Autowired
    private CryptoAddressService cryptoAddressService;

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

    @Override
    public long count(CryptoTransaction record) {
        return cryptoTransactionRepository.count(Example.of(record));
    }

    @Override
    public void handleWithdraw() {
        List<CryptoTransaction> transactionList = findList(CryptoTransaction.builder()
                .type(CryptoTransaction.TransactionType.WITHDRAWAL.getType())
                .status(CryptoTransaction.TransactionStatus.APPROVE.getStatus())
                .build());
        transactionList.forEach(transaction -> {
            try {
                handleWithdraw(transaction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (UnsupportedException e) {
                e.printStackTrace();
            } catch (BalanceNotEnoughException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleWithdraw(CryptoTransaction transaction) throws IOException, BalanceNotEnoughException, CipherException, UnsupportedException, ExecutionException, InterruptedException {
        BigDecimal amount = transaction.getAmount();
        CryptoCoin coin = transaction.getCoin();
        String toAddress = transaction.getToAddress();
        List<CryptoAddress> addressList = cryptoAddressService.findList(CryptoAddress.builder()
                .type(CryptoAddress.CoinType.SYS.getType())
                .build());
        CryptoAddress fromAddress = null;
        for (CryptoAddress address : addressList) {
            long counter = count(CryptoTransaction.builder()
                    .fromAddress(address.getAddress())
                    .status(CryptoTransaction.TransactionStatus.PENDING.getStatus())
                    .build());
            if (counter > 0) {
                continue;
            }
            BigDecimal balance = EthereumUtils.getBalance(address.getAddress(), coin.getContract(), coin.getDecimals());
            if (balance.compareTo(amount) == -1) {
                continue;
            } else {
                fromAddress = address;
                break;
            }
        }
        if (fromAddress == null) {
            throw new BalanceNotEnoughException();
        }
        transaction.setFromAddress(fromAddress.getAddress());
        transaction.setGasPrice(coin.getGasPrice());
        transaction.setGasLimit(coin.getGasLimit());
        /////////////////////////////
        // 禁止已任何形式输出该值！！！//
        /////////////////////////////
        String privateKey = EthereumUtils.getPrivateKey(fromAddress.getKeystore(), fromAddress.getPassword());
        String signMessage = null;
        if (coin.getType() == CryptoCoin.CoinType.MAIN.getType()) {
            signMessage = EthereumUtils.signMessage(
                    privateKey,
                    transaction.getToAddress(),
                    transaction.getAmount(),
                    transaction.getGasPrice(),
                    transaction.getGasLimit());
        } else if (coin.getType() == CryptoCoin.CoinType.ERC20.getType()) {
            signMessage = EthereumUtils.signMessage(
                    privateKey,
                    transaction.getToAddress(),
                    coin.getContract(),
                    transaction.getAmount(),
                    coin.getDecimals(),
                    transaction.getGasPrice(),
                    transaction.getGasLimit());
        } else {
            throw  new UnsupportedException();
        }
        String transactionHash = EthereumUtils.transaction(signMessage);
        save(CryptoTransaction.builder()
                .id(transaction.getId())
                .transactionHash(transactionHash)
                .status(CryptoTransaction.TransactionStatus.PENDING.getStatus())
                .build());
    }
}
