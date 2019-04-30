package com.my.demo.wallet.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class EthereumUtils {
    private static final Logger logger = LoggerFactory.getLogger(EthereumUtils.class);

    private static final String PASSWORD_SALT = "cT9i5BZK";

    public static final String DEFAULT_PASSWORD = "tR0JTb5Q";

    private static String KEYSTORE_PATH;

    @Value("${ethereum.wallet.keystore.path:./keystore}")
    public static void setKeystorePath(String keystorePath) {
        File keystore = new File(keystorePath);
        if (!keystore.isDirectory()) {
            logger.info("==>Create keystore directory : {}", keystorePath);
            keystore.mkdir();
        }
        KEYSTORE_PATH = keystorePath;
    }

    private static String URL;

    @Value("${ethereum.rpcClient.url:http://localhost:8545}")
    public static void setUrl(String url) {
        URL = url;
    }

    private static final int RADIX = 16;

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1_000_000_000L);// gas_price gas_limit

    private static final BigInteger GAS_LIMIT_ETHER = BigInteger.valueOf(21000);// 0.000021000000000000

    private static final BigInteger GAS_LIMIT_ERC20 = BigInteger.valueOf(311332);

    private static Web3j web3j;

    public static Web3j getWeb3j() {
        if (null == web3j) {
            logger.info("==>Web3j client building, URL is {}", URL);
            web3j = Web3j.build(new HttpService(URL));
        }
        return web3j;
    }

    public static String generateNewWalletFile() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        return generateNewWalletFile(DEFAULT_PASSWORD);
    }

    public static String generateNewWalletFile(String passwordPlaintext) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String password = DigestUtils.md5Hex(passwordPlaintext + PASSWORD_SALT);
        String fileName = WalletUtils.generateNewWalletFile(password, new File(KEYSTORE_PATH), true);
        return fileName;
    }

    public static BigDecimal getBalance(String address, String contract, int decimals) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        Function function = new Function("balanceOf",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(null, contract, data);
        String result = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send().getResult();
        BigInteger balance = Numeric.decodeQuantity(result);
        return new BigDecimal(balance).divide(BigDecimal.TEN.pow(decimals));
    }

    public static BigDecimal getBalance(String address, int decimals) throws IOException {
        BigInteger balance = getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        return new BigDecimal(balance).divide(BigDecimal.TEN.pow(decimals));
    }

    public static BigDecimal getBalance(String address) throws Exception {
        BigInteger balance = getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        return Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
    }

    public static BigInteger getDecimals(String contract) throws IOException {
        List<Type> inputParameters = new ArrayList<>();
        Function function = new Function("decimals",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(null, contract, data);
        String result = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send().getResult();
        return Numeric.decodeQuantity(result);
    }

    /**
     * 0x000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000044149504300000000000000000000000000000000000000000000000000000000
     *
     * 最大长度32的字符
     * 0000000000000000000000000000000000000000000000000000000000000020
     *
     * 由4的字符构成
     * 0000000000000000000000000000000000000000000000000000000000000004
     *
     * 内容
     * 4149504300000000000000000000000000000000000000000000000000000000
     *
     * @param contract
     * @return
     * @throws IOException
     */
    public static String getSymbol(String contract) throws IOException {
        List<Type> inputParameters = new ArrayList<>();
        Function function = new Function("symbol",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(null, contract, data);
        String result = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send().getResult();
        if ("0x".equals(result)) {
            return null;
        }
        result = result.replace("0x", "");
        BigInteger length = new BigInteger(result.substring(64, 2 * 64));
        String content = result.substring(2 * 64, 2 * 64 + 2 * length.intValue());
        System.out.println(content);
        return new String(Numeric.hexStringToByteArray(content));
    }

    public static String getName(String contract) throws IOException {
        List<Type> inputParameters = new ArrayList<>();
        Function function = new Function("name",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(null, contract, data);
        String result = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send().getResult();
        if ("0x".equals(result)) {
            return null;
        }
        result = result.replace("0x", "");
        BigInteger length = new BigInteger(result.substring(64, 2 * 64));
        String content = result.substring(2 * 64, 2 * 64 + 2 * length.intValue());
        System.out.println(content);
        return new String(Numeric.hexStringToByteArray(content));
    }

    public static BigDecimal getTotalSupply(String contract, int decimals) throws IOException {
        List<Type> inputParameters = new ArrayList<>();
        Function function = new Function("totalSupply",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(null, contract, data);
        String result = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send().getResult();
        BigInteger totalSupply = Numeric.decodeQuantity(result);
        return new BigDecimal(totalSupply).divide(BigDecimal.TEN.pow(decimals));
    }

    public static String getPrivateKey(String keystore) throws IOException, CipherException {
        return getPrivateKey(DEFAULT_PASSWORD, new File(KEYSTORE_PATH, keystore));
    }

    public static String getPrivateKey(File keystore) throws IOException, CipherException {
        return getPrivateKey(DEFAULT_PASSWORD, keystore);
    }

    public static String getPrivateKey(String password, String keystore) throws IOException, CipherException {
        return getPrivateKey(password, new File(KEYSTORE_PATH, keystore));
    }

    public static String getPrivateKey(String password, File keystore) throws IOException, CipherException {
        password = DigestUtils.md5Hex(password + PASSWORD_SALT);
        logger.debug("password:{}", password);
        Credentials credentials = WalletUtils.loadCredentials(password, keystore);
        return credentials.getEcKeyPair().getPrivateKey().toString(RADIX);
    }

    public static String signMessage(String privateKey, String to, BigDecimal value) throws ExecutionException, InterruptedException {
        return signMessage(privateKey, to, value.multiply(BigDecimal.TEN.pow(18)).toBigInteger(), GAS_PRICE, GAS_LIMIT_ETHER);
    }

    public static String signMessage(String privateKey, String to, BigDecimal value, BigInteger gasPrice, BigInteger gasLimit) throws ExecutionException, InterruptedException {
        return signMessage(privateKey, to, value.multiply(BigDecimal.TEN.pow(18)).toBigInteger(), gasPrice, gasLimit);
    }

    public static String signMessage(String privateKey, String to, String contract, BigDecimal value, int decimals) throws ExecutionException, InterruptedException {
        return signMessage(privateKey, to, contract, value.multiply(BigDecimal.TEN.pow(decimals)).toBigInteger(), GAS_PRICE, GAS_LIMIT_ERC20);
    }

    public static String signMessage(String privateKey, String to, String contract, BigDecimal value, int decimals, BigInteger gasPrice, BigInteger gasLimit) throws ExecutionException, InterruptedException {
        return signMessage(privateKey, to, contract, value.multiply(BigDecimal.TEN.pow(decimals)).toBigInteger(), gasPrice, gasLimit);
    }

    private static String signMessage(String privateKey, String to, BigInteger value, BigInteger gasPrice, BigInteger gasLimit) throws ExecutionException, InterruptedException {
        Credentials credentials = Credentials.create(privateKey);
        EthGetTransactionCount ethGetTransactionCount = getWeb3j().ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexString = Numeric.toHexString(signedMessage);
        return hexString;
    }

    private static String signMessage(String privateKey, String to, String contract, BigInteger value, BigInteger gasPrice, BigInteger gasLimit) throws ExecutionException, InterruptedException {
        Credentials credentials = Credentials.create(privateKey);
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(to));
        inputParameters.add(new Uint256(value));
        Function function = new Function("transfer",
                inputParameters,
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        EthGetTransactionCount ethGetTransactionCount = getWeb3j().ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contract, data);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexString = Numeric.toHexString(signedMessage);
        return hexString;
    }

    public static String transaction(String signMessage) throws ExecutionException, InterruptedException {
        EthSendTransaction ethSendTransaction = getWeb3j().ethSendRawTransaction(signMessage).sendAsync().get();
        if (ethSendTransaction.hasError()) {
            String errerMassage = ethSendTransaction.getError().getMessage();
            logger.error("transaction with error : {}", errerMassage);
            throw new RuntimeException(errerMassage);
        } else {
            return ethSendTransaction.getTransactionHash();
        }
    }

    public static org.web3j.protocol.core.methods.response.Transaction getTransaction(String transactionHash) throws Exception {
        EthTransaction ethTransaction = getWeb3j().ethGetTransactionByHash(transactionHash).send();
        return ethTransaction.getTransaction().get();
    }

    public static BigInteger getBlockNumber() throws IOException {
        return getWeb3j().ethBlockNumber().send().getBlockNumber();
    }

    public static List<EthBlock.TransactionResult> getTransactionList(BigInteger blockNumber) throws IOException {
        List<EthBlock.TransactionResult> txList = getWeb3j().ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send().getBlock().getTransactions();
        return txList;
    }

    /**
     * Function: transfer(address _to, uint256 _value) ***
     *
     * MethodID: 0xa9059cbb
     * [0]:  0000000000000000000000009db0976d0203503fd3e03bfc1c4e23e5fecd0094
     * [1]:  0000000000000000000000000000000000000000000000008ac7230489e80000
     * @param input
     */
    public static void parseInput(String input) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodId = input.substring(0, 10);
        String _to = input.substring(10, 74);
        String _value = input.substring(74);
        Method refMethod = null;
        refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
        refMethod.setAccessible(true);
        Address to = (Address) refMethod.invoke(null, _to, 0, Address.class);
        Uint256 value = (Uint256) refMethod.invoke(null, _value, 0, Uint256.class);
        System.out.printf("MethodID: %s\n", methodId);
        System.out.printf("[0]: %s\n", to.toString());
        System.out.printf("[1]: %s\n", value.getValue());
    }
}