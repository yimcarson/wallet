package com.my.demo.wallet.util;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet2Params;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BitcoinUtilsTest {
    @Test
    public void testFetch() {
        try {
            NetworkParameters params = TestNet3Params.get();
            BlockStore blockStore = new MemoryBlockStore(params);
            BlockChain chain = new BlockChain(params, blockStore);
            PeerGroup peerGroup = new PeerGroup(params, chain);
            peerGroup.addPeerDiscovery(new DnsDiscovery(params));
            peerGroup.start();
            peerGroup.waitForPeers(1).get();
            Peer peer = peerGroup.getConnectedPeers().get(0);

            Sha256Hash blockHash = Sha256Hash.wrap("000000000000033c2ab26a17be58723d6cfa15b57fb7cd3c1a4e4b447ef31669");
            Future<Block> future = peer.getBlock(blockHash);
            Block block = future.get();
            System.out.println(block);

            int blockHeightDifference = peer.getPeerBlockHeightDifference();
            System.out.println(blockHeightDifference);
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void testFetchGenesisBlock() {

    }

    @Test
    public void testCreatWallet() {
        try {
            File walletDat = new File("/Users/yimcarson/Workspace/GitHub/wallet/wallets", "wallet.dat");
            if (!walletDat.exists()) {
                walletDat.createNewFile();
            }
            Wallet wallet = new Wallet(TestNet3Params.get());
            wallet.saveToFile(walletDat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testLoadFromFile() {
        try {
            File walletDat = new File("/Users/yimcarson/Workspace/GitHub/wallet/wallets", "wallet.dat");
            if (!walletDat.exists()) {
                walletDat.createNewFile();
            }
            Wallet wallet = Wallet.loadFromFile(walletDat);
//            wallet.decrypt("123456");
            System.out.println(wallet);
//            System.out.println(wallet.currentReceiveAddress());
            List<ECKey> keyList = wallet.getImportedKeys();
            for (ECKey key : keyList) {
                System.out.println(key.toAddress(MainNetParams.get()));
                System.out.printf("Private Key : %s\n", key.getPrivateKeyAsHex());
            }
            System.out.println(wallet.getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImportKeys() {
        try {
            File walletDat = new File("/Users/yimcarson/Workspace/GitHub/wallet/wallets", "wallet.dat");
            if (!walletDat.exists()) {
                walletDat.createNewFile();
            }
            Wallet wallet = Wallet.loadFromFile(walletDat);
//            wallet.decrypt("123456");
            List<ECKey> keyList = wallet.getImportedKeys();
            for (int i = 0; i < 1; i++) {
                keyList.add(new ECKey());
            }
            wallet.importKeys(keyList);
            wallet.saveToFile(walletDat);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMnemonicCode() {
        DeterministicSeed seed = new DeterministicSeed(new SecureRandom(),128,"123456", Utils.currentTimeSeconds());
        List<String> mnemonicCode = seed.getMnemonicCode();
        System.out.println(mnemonicCode);
        // [other, logic, priority, endless, west, link, traffic, region, mimic, room, grain, nation]
    }

    @Test
    public void testFromSeed() {
        try {
            String mnemonicCode = "sudden icon plug inquiry barely parade when fog jar decorate clip insane";
//            long creationTimeSeconds = Utils.currentTimeSeconds();
            long creationTimeSeconds = 1556253193L;
            DeterministicSeed seed = new DeterministicSeed(mnemonicCode, null, "", creationTimeSeconds);
            Wallet wallet = Wallet.fromSeed(TestNet3Params.get(), seed);
            System.out.println(wallet);
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendCoin() {
        try {
            // mtPTDNEvTbefYx8GQCYncUNEYN7MKmQxeK
            // mrfHj9h7ab5AadSXc181WYXACrcsMSpWVD
            Address to = Address.fromBase58(TestNet3Params.get(), "mtPTDNEvTbefYx8GQCYncUNEYN7MKmQxeK");
            Coin value = Coin.parseCoin("0.001");
            List<ECKey> ownerKeys = new ArrayList<>();
            // cRZTtRZf1HfmfdLMxruCyxBQscAvXH6XijGbj3g6aGKAR3g2CXzL
            // 9b91372f45e69253d37c954cced9e41b5695eb23899f20cdd096808b9971a016

//            ECKey ownerKey = ECKey.fromPrivate(Base58.decode("cRZTtRZf1HfmfdLMxruCyxBQscAvXH6XijGbj3g6aGKAR3g2CXzL"));
            ECKey ownerKey = ECKey.fromPrivate(new BigInteger("9b91372f45e69253d37c954cced9e41b5695eb23899f20cdd096808b9971a016", 16));
            Address ownerAddress = ownerKey.toAddress(TestNet3Params.get());
            System.out.println(ownerAddress); // mrfHj9h7ab5AadSXc181WYXACrcsMSpWVD
            Transaction transaction = new Transaction(TestNet3Params.get());
            transaction.addOutput(value, to);
            System.out.println(transaction.getHashAsString());


            Wallet wallet = Wallet.fromKeys(TestNet3Params.get(), ownerKeys);
            Coin balance = wallet.getBalance();
            System.out.println(balance);
            WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, transaction);


            SendRequest request = SendRequest.to(to, value);
            request.changeAddress = ownerAddress;


            Transaction tx = wallet.sendCoinsOffline(request);
            System.out.println(tx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBiginteger() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        /*
        0ca32dc3
        dd464ef3
        b33745ff
        20321b82
         */
        System.out.println(new BigInteger("0ca32dc3dd464ef3b33745ff20321b825d7660fe09b2431583b68d8b3c8297b2", 16));
    }
}