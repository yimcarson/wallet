package com.my.demo.wallet.util;


import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.TypeDecoder;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public class EthereumUtilsTest {
    @Before
    public void init() {
        EthereumUtils.setKeystorePath("./keystore");
        EthereumUtils.setUrl("https://ropsten.infura.io/v3/12ab05a5e3b24d75b4c43a57c5683169");
    }

    @Test
    public void testGenerateNewWalletFile() {
        try {
            EthereumUtils.generateNewWalletFile();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPrivateKey() {
        try {
            String privateKey = EthereumUtils.getPrivateKey("UTC--2019-04-24T04-57-23.490000000Z--edc66095d5e2109531c47b045312afd60bbcc5f8.json");
            System.out.println(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBalance() {
        try {
            BigDecimal balance = EthereumUtils.getBalance("0xedc66095d5e2109531c47b045312afd60bbcc5f8");
            System.out.println(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBlockNumber() {
        try {
            BigInteger blockNumber = EthereumUtils.getBlockNumber();
            System.out.println(blockNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSymbol() {
        // 0x301d65ea5e3b6d34fe359985421414cca8010c51
        // 0x136f8d2ab5c4b60068a7925d26bf08de18ba4a97
        try {
            String symbol = EthereumUtils.getSymbol("0x301d65ea5e3b6d34fe359985421414cca8010c51");
            System.out.println(symbol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetName() {
        try {
            String name = EthereumUtils.getName("0x301d65ea5e3b6d34fe359985421414cca8010c51");
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTransactionList() {
        try {
            List<EthBlock.TransactionResult> txList = EthereumUtils.getTransactionList(BigInteger.valueOf(5467647L));
            txList.forEach(tx -> {
                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
                BigInteger blockNumber = transaction.getBlockNumber();
                String hash = transaction.getHash();
                String from = transaction.getFrom();
                String to = transaction.getTo();
                BigInteger value = transaction.getValue();
                String valueRaw = transaction.getValueRaw();
                String input = transaction.getInput();
                BigInteger nonce = transaction.getNonce();
                String nonceRaw = transaction.getNonceRaw();
                long v = transaction.getV();
                String blockHash = transaction.getBlockHash();
                String blockNumberRaw = transaction.getBlockNumberRaw();
                Long chainId = transaction.getChainId();
                String creates = transaction.getCreates();
                BigInteger gas = transaction.getGas();
                String gasRaw = transaction.getGasRaw();
                BigInteger gasPrice = transaction.getGasPrice();
                String gasPriceRaw = transaction.getGasPriceRaw();
                String publicKey = transaction.getPublicKey();
                String raw = transaction.getRaw();
                String s = transaction.getS();
                String r = transaction.getR();
                BigInteger transactionIndex = transaction.getTransactionIndex();
                String transactionIndexRaw = transaction.getTransactionIndexRaw();
                System.out.println(transaction.getHash());

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
