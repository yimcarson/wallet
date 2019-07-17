package com.my.demo.wallet.utils;


import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
//        EthereumUtils.setUrl("https://mainnet.infura.io/v3/12ab05a5e3b24d75b4c43a57c5683169");
//        EthereumUtils.setUrl("https://ropsten.infura.io/v3/12ab05a5e3b24d75b4c43a57c5683169");
//        EthereumUtils.setUrl("https://rinkeby.infura.io/v3/12ab05a5e3b24d75b4c43a57c5683169");
//        EthereumUtils.setUrl("http://47.75.177.252:8545");
//        EthereumUtils.setUrl("http://192.168.1.200:8545");
        EthereumUtils.setUrl("http://47.91.224.65:8545");
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
            String privateKey = EthereumUtils.getPrivateKey("vppj6B5DHO26fI8z", "UTC--2019-05-08T10-47-08.124534678Z--f7020248e3a33a92cc1d3eeb038935d8ede86ea3");
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
            BigDecimal balance = EthereumUtils.getBalance("0x92C417ae0cdC3e0e148d48A770ca9159D621FBF6", "0xf749b654531f62ab24d5d4243bb8b27e702102c8", 5);
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
            String symbol = EthereumUtils.getSymbol("0xf749b654531f62ab24d5d4243bb8b27e702102c8");
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
    public void testGetTransaction() {
        try {
            Transaction transaction = transaction = EthereumUtils.getTransaction("0x42797b0f19bada1abc6693f69cdbf81b4cfc247350543482ba9e2d35fb2d8cb8");
            BigInteger blockNumber = transaction.getBlockNumber();// 5455188
            String hash = transaction.getHash(); // 0x42797b0f19bada1abc6693f69cdbf81b4cfc247350543482ba9e2d35fb2d8cb8
            String from = transaction.getFrom(); // 0x01e02c56248359183ce361c4bced3f939183f42d
            String to = transaction.getTo(); // 0x31bc5f4c55d9aba40297ed4083c76fd652b90ee1
            BigInteger value = transaction.getValue(); // 0
            String valueRaw = transaction.getValueRaw(); // 0x0
            String input = transaction.getInput(); // 0xa9059cbb00000000000000000000000092c417ae0cdc3e0e148d48a770ca9159d621fbf60000000000000000000000000000000000000000033b2e3c9fd0803ce8000000
            BigInteger nonce = transaction.getNonce(); // 62
            String nonceRaw = transaction.getNonceRaw(); // 0x3e
            String blockHash = transaction.getBlockHash(); // 0x22b5e088eebae98280b2c58588f6432f2fcb4e70b17b10d263832617e2192468
            String blockNumberRaw = transaction.getBlockNumberRaw(); // 0x533d54
            Long chainId = transaction.getChainId(); // 3
            String creates = transaction.getCreates(); // null
            BigInteger gas = transaction.getGas(); // 54757
            String gasRaw = transaction.getGasRaw(); // 0xd5e5
            BigInteger gasPrice = transaction.getGasPrice(); // 10000000000
            String gasPriceRaw = transaction.getGasPriceRaw(); // 0x2540be400
            String publicKey = transaction.getPublicKey(); // null
            String raw = transaction.getRaw(); // null
            long v = transaction.getV(); // 42
            String s = transaction.getS(); // 0x4624528ab3b9d75616bcacbb535ad82ee4c0e1b47905b24a2521bd0ff3875c2a
            String r = transaction.getR(); // 0xab215a948bc34e000260943b8346bf357e949094194e384ac9d9caf205f2288a
            BigInteger transactionIndex = transaction.getTransactionIndex(); // 1
            String transactionIndexRaw = transaction.getTransactionIndexRaw(); // 0x1
            System.out.println(transaction.getHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTransactionList() {
        try {
            List<EthBlock.TransactionResult> txList = EthereumUtils.getTransactionList(BigInteger.valueOf(5467647L));
            txList.forEach(tx -> {
//                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
                Transaction transaction = (Transaction) tx.get();
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

    @Test
    public void testParseInput() {
        try {
            //0x
            // a9059cbb
            // 00000000000000000000000092c417ae0cdc3e0e148d48a770ca9159d621fbf6
            // 0000000000000000000000000000000000000000033b2e3c9fd0803ce8000000
            EthereumUtils.parseInput("0xa9059cbb00000000000000000000000092c417ae0cdc3e0e148d48a770ca9159d621fbf60000000000000000000000000000000000000000033b2e3c9fd0803ce8000000");
            System.out.println(new BigInteger("a9059cbb", 16));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
