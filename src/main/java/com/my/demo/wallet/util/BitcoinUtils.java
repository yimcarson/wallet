package com.my.demo.wallet.util;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;

public class BitcoinUtils {

    private static final NetworkParameters networkParameters = TestNet3Params.get();

    public static void generateNewAddress() {
        ECKey key = new ECKey();
        key.getPrivateKeyAsHex();
        key.getPublicKeyAsHex();
        Address address = key.toAddress(networkParameters);
        System.out.println(address);
    }
}
