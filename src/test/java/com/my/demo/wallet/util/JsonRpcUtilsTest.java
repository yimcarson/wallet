package com.my.demo.wallet.util;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import net.iharder.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.*;

/**
 * == Blockchain ==
 *     callcontract "address" "data" ( address )
 *     getaccountinfo "address"
 *     getbestblockhash
 *     getblock "blockhash" ( verbosity )
 *     getblockchaininfo
 *             getblockcount
 *     getblockhash height
 *     getblockheader "hash" ( verbose )
 *     getchaintips
 *     getchaintxstats ( nblocks blockhash )
 *     getdifficulty
 *     getmempoolancestors txid (verbose)
 *     getmempooldescendants txid (verbose)
 *     getmempoolentry txid
 *     getmempoolinfo
 *     getrawmempool ( verbose )
 *     getstorage "address"
 *     gettransactionreceipt "hash"
 *     gettxout "txid" n ( include_mempool )
 *     gettxoutproof ["txid",...] ( blockhash )
 *     gettxoutsetinfo
 *     listcontracts (start maxDisplay)
 *     preciousblock "blockhash"
 *     pruneblockchain
 *             savemempool
 *     searchlogs <fromBlock> <toBlock> (address) (topics)
 *     verifychain ( checklevel nblocks )
 *     verifytxoutproof "proof"
 *     waitforlogs (fromBlock) (toBlock) (filter) (minconf)
 *
 *             == Control ==
 *     getmemoryinfo ("mode")
 *     help ( "command" )
 *     logging ( <include> <exclude> )
 *     stop
 *             uptime
 *
 * == Generating ==
 *     generate nblocks ( maxtries )
 *     generatetoaddress nblocks address (maxtries)
 *
 * == Mining ==
 *     getblocktemplate ( TemplateRequest )
 *     getmininginfo
 *     getnetworkhashps ( nblocks height )
 *     getstakinginfo
 *     getsubsidy [nTarget]
 *     prioritisetransaction <txid> <dummy value> <fee delta>
 *     submitblock "hexdata"  ( "dummy" )
 *
 *             == Network ==
 *     addnode "node" "add|remove|onetry"
 *     clearbanned
 *     disconnectnode "[address]" [nodeid]
 *     getaddednodeinfo ( "node" )
 *     getconnectioncount
 *             getnettotals
 *     getnetworkinfo
 *             getpeerinfo
 *     listbanned
 *             ping
 *     setban "subnet" "add|remove" (bantime) (absolute)
 *     setnetworkactive true|false
 *
 *             == Rawtransactions ==
 *     combinerawtransaction ["hexstring",...]
 *     createrawtransaction [{"txid":"id","vout":n},...] {"address":amount,"data":"hex",...} ( locktime ) ( replaceable )
 *     decoderawtransaction "hexstring" ( iswitness )
 *     decodescript "hexstring"
 *     fromhexaddress "hexaddress"
 *     fundrawtransaction "hexstring" ( options iswitness )
 *     gethexaddress "address"
 *     getrawtransaction "txid" ( verbose "blockhash" )
 *     sendrawtransaction "hexstring" ( allowhighfees )
 *     signrawtransaction "hexstring" ( [{"txid":"id","vout":n,"scriptPubKey":"hex","redeemScript":"hex"},...] ["privatekey1",...] sighashtype )
 *
 *             == Util ==
 *     createmultisig nrequired ["key",...]
 *     estimatefee nblocks
 *     estimatesmartfee conf_target ("estimate_mode")
 *     signmessagewithprivkey "privkey" "message"
 *     validateaddress "address"
 *     verifymessage "address" "signature" "message"
 *
 *             == Wallet ==
 *     abandontransaction "txid"
 *     abortrescan
 *     addmultisigaddress nrequired ["key",...] ( "account" "address_type" )
 *     backupwallet "destination"
 *     bumpfee "txid" ( options )
 *     createcontract "bytecode" (gaslimit gasprice "senderaddress" broadcast)
 *     dumpprivkey "address"
 *     dumpwallet "filename"
 *     getaccount "address"
 *     getaccountaddress "account"
 *     getaddressesbyaccount "account"
 *     getbalance ( "account" minconf include_watchonly )
 *     getnewaddress ( "account" "address_type" )
 *     getrawchangeaddress ( "address_type" )
 *     getreceivedbyaccount "account" ( minconf )
 *     getreceivedbyaddress "address" ( minconf )
 *     gettransaction "txid" ( include_watchonly ) (waitconf)
 *     getunconfirmedbalance
 *             getwalletinfo
 *     importaddress "address" ( "label" rescan p2sh )
 *     importmulti "requests" ( "options" )
 *     importprivkey "qtumprivkey" ( "label" ) ( rescan )
 *     importprunedfunds
 *     importpubkey "pubkey" ( "label" rescan )
 *     importwallet "filename"
 *     keypoolrefill ( newsize )
 *     listaccounts ( minconf include_watchonly)
 *     listaddressgroupings
 *             listlockunspent
 *     listreceivedbyaccount ( minconf include_empty include_watchonly)
 *     listreceivedbyaddress ( minconf include_empty include_watchonly)
 *     listsinceblock ( "blockhash" target_confirmations include_watchonly include_removed )
 *     listtransactions ( "account" count skip include_watchonly)
 *     listunspent ( minconf maxconf  ["addresses",...] [include_unsafe] [query_options])
 *     listwallets
 *     lockunspent unlock ([{"txid":"txid","vout":n},...])
 *     move "fromaccount" "toaccount" amount ( minconf "comment" )
 *     removeprunedfunds "txid"
 *     rescanblockchain ("start_height") ("stop_height")
 *     reservebalance [<reserve> [amount]]
 *     sendfrom "fromaccount" "toaddress" amount ( minconf "comment" "comment_to" )
 *     sendmany "fromaccount" {"address":amount,...} ( minconf "comment" ["address",...] replaceable conf_target "estimate_mode")
 *     sendmanywithdupes "fromaccount" {"address":amount,...} ( minconf "comment" ["address",...] )
 *     sendtoaddress "address" amount ( "comment" "comment_to" subtractfeefromamount replaceable conf_target "estimate_mode")
 *     sendtocontract "contractaddress" "data" (amount gaslimit gasprice senderaddress broadcast)
 *     setaccount "address" "account"
 *     settxfee amount
 *     signmessage "address" "message"
 *     walletlock
 *     walletpassphrase "passphrase" timeout stakingonly
 *     walletpassphrasechange "oldpassphrase" "newpassphrase"
 */
public class JsonRpcUtilsTest {
    private static final String RPC_HOST = "47.75.177.252";

    private static final String RPC_PORT = "18332";

    private static final String RPC_USER = "test";

    private static final String RPC_PASS = "123456";

    private JsonRpcHttpClient client;

    private String methodName;

    private Object[] argument;

    private Class clazz;

    private Object result;

    @Before
    public void init() throws Exception  {
        String cred = Base64.encodeBytes((RPC_USER + ":" + RPC_PASS).getBytes());
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + cred);
        System.out.println("Basic " + cred);
        headers.put("Content-Type", "application/json");
        client = new JsonRpcHttpClient(new URL("http://" + RPC_HOST + ":" + RPC_PORT), headers);
    }

    @After
    public void invoke() throws Throwable {
        result = client.invoke(methodName, argument, clazz);
        System.out.println(result);
    }

    @Test
    public void getwalletinfo(){
        methodName = "getwalletinfo";
        argument = new Object[]{};
        clazz = Object.class;
    }

    /**
     * 创建合约（量子链）
     */
    @Test
    public void createcontract() {
//        testwalletpassphrase();
        String contract = "60806040526012600260006101000a81548160ff021916908360ff1602179055503480156200002d57600080fd5b506040516200140f3803806200140f833981018060405260608110156200005357600080fd5b810190808051906020019092919080516401000000008111156200007657600080fd5b828101905060208101848111156200008d57600080fd5b8151856001820283011164010000000082111715620000ab57600080fd5b50509291906020018051640100000000811115620000c857600080fd5b82810190506020810184811115620000df57600080fd5b8151856001820283011164010000000082111715620000fd57600080fd5b5050929190505050600260009054906101000a900460ff1660ff16600a0a8302600381905550600354600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550816000908051906020019062000181929190620001a4565b5080600190805190602001906200019a929190620001a4565b5050505062000253565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620001e757805160ff191683800117855562000218565b8280016001018555821562000218579182015b8281111562000217578251825591602001919060010190620001fa565b5b5090506200022791906200022b565b5090565b6200025091905b808211156200024c57600081600090555060010162000232565b5090565b90565b6111ac80620002636000396000f3fe608060405234801561001057600080fd5b50600436106100d1576000357c01000000000000000000000000000000000000000000000000000000009004806370a082311161008e57806370a08231146102cd57806379cc67901461032557806395d89b411461038b578063a9059cbb1461040e578063cae9ca5114610474578063dd62ed3e14610571576100d1565b806306fdde03146100d6578063095ea7b31461015957806318160ddd146101bf57806323b872dd146101dd578063313ce5671461026357806342966c6814610287575b600080fd5b6100de6105e9565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561011e578082015181840152602081019050610103565b50505050905090810190601f16801561014b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101a56004803603604081101561016f57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610687565b604051808215151515815260200191505060405180910390f35b6101c7610779565b6040518082815260200191505060405180910390f35b610249600480360360608110156101f357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061077f565b604051808215151515815260200191505060405180910390f35b61026b6108ac565b604051808260ff1660ff16815260200191505060405180910390f35b6102b36004803603602081101561029d57600080fd5b81019080803590602001909291905050506108bf565b604051808215151515815260200191505060405180910390f35b61030f600480360360208110156102e357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506109c3565b6040518082815260200191505060405180910390f35b6103716004803603604081101561033b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506109db565b604051808215151515815260200191505060405180910390f35b610393610bf5565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103d35780820151818401526020810190506103b8565b50505050905090810190601f1680156104005780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61045a6004803603604081101561042457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610c93565b604051808215151515815260200191505060405180910390f35b6105576004803603606081101561048a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001906401000000008111156104d157600080fd5b8201836020820111156104e357600080fd5b8035906020019184600183028401116401000000008311171561050557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610caa565b604051808215151515815260200191505060405180910390f35b6105d36004803603604081101561058757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610e2e565b6040518082815260200191505060405180910390f35b60008054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561067f5780601f106106545761010080835404028352916020019161067f565b820191906000526020600020905b81548152906001019060200180831161066257829003601f168201915b505050505081565b600081600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b60035481565b6000600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054821115151561080c57600080fd5b81600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055506108a1848484610e53565b600190509392505050565b600260009054906101000a900460ff1681565b600081600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015151561090f57600080fd5b81600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550816003600082825403925050819055503373ffffffffffffffffffffffffffffffffffffffff167fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca5836040518082815260200191505060405180910390a260019050919050565b60046020528060005260406000206000915090505481565b600081600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610a2b57600080fd5b600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548211151515610ab657600080fd5b81600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550816003600082825403925050819055508273ffffffffffffffffffffffffffffffffffffffff167fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca5836040518082815260200191505060405180910390a26001905092915050565b60018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c8b5780601f10610c6057610100808354040283529160200191610c8b565b820191906000526020600020905b815481529060010190602001808311610c6e57829003601f168201915b505050505081565b6000610ca0338484610e53565b6001905092915050565b600080849050610cba8585610687565b15610e25578073ffffffffffffffffffffffffffffffffffffffff16638f4ffcb1338630876040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610db4578082015181840152602081019050610d99565b50505050905090810190601f168015610de15780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b158015610e0357600080fd5b505af1158015610e17573d6000803e3d6000fd5b505050506001915050610e27565b505b9392505050565b6005602052816000526040600020602052806000526040600020600091509150505481565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614151515610e8f57600080fd5b80600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610edd57600080fd5b600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401111515610f6b57600080fd5b6000600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401905081600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a380600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054600460008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540114151561117a57fe5b5050505056fea165627a7a723058205cc82e880aa00a5ad8ef00827b6681f117e3d43e4610e5ee809e1f9d0a3d824f0029";
        methodName = "createcontract";
        argument = new Object[]{contract};
        clazz = Object.class;
        // 手续费
        // {"result":null,"error":{"code":-6,"message":"Insufficient funds"},"id":"940957090"}
    }

    /**
     * 解锁钱包
     * Request {"id":"286815546","jsonrpc":"2.0","method":"walletpassphrase","params":["123456",10000]}
     * JSON-PRC Response: {"result":null,"error":null,"id":"286815546"}
     */
    @Test
    public void walletpassphrase() {
        String password = "123456";
        Long timeOut = 10000L;
        Boolean stakingonly = true;// QTUM
        methodName = "walletpassphrase";
        // argument = new Object[]{password, timeOut, stakingonly};
        argument = new Object[]{password, timeOut};
        clazz = Object.class;
    }

    /**
     * 加密钱包（成功会关闭钱包）
     */
    @Test
    public void encryptwallet() {
        methodName = "encryptwallet";
        argument = new Object[]{"123456"};
        clazz = Object.class;

    }

    /**
     * 创建地址
     * Request {"id":"368387099","jsonrpc":"2.0","method":"getnewaddress","params":["test_user_1"]}
     * JSON-PRC Response: {"result":"2N2q9imkFkp6EjP6GovH5ytabBURUrEbMw3","error":null,"id":"368387099"}
     */
    @Test
    public void getnewaddress() {
        methodName = "getnewaddress";
        argument = new Object[]{"test_user_1"};
        clazz = Object.class;
    }

    /**
     * 获取地址
     * Request {"id":"79310420","jsonrpc":"2.0","method":"getaddressesbyaccount","params":["test_user_1"]}
     * JSON-PRC Response: {"result":["2N2q9imkFkp6EjP6GovH5ytabBURUrEbMw3"],"error":null,"id":"79310420"}
     */
    @Test
    public void getaddressesbyaccount() {
        methodName = "getaddressesbyaccount";
        argument = new Object[]{"test_user_1"};
        clazz = Object.class;
    }

    /**
     * 获取余额
     * Request {"id":"33199120","jsonrpc":"2.0","method":"getbalance"}
     * JSON-PRC Response: {"result":0.0,"error":null,"id":"33199120"}
     */
    @Test
    public void getbalance() {
        methodName = "getbalance";
        argument = new Object[]{};
        clazz = Object.class;
    }

    /**
     * 导出私钥
     * Request {"id":"1604073600","jsonrpc":"2.0","method":"dumpprivkey","params":["2N2q9imkFkp6EjP6GovH5ytabBURUrEbMw3"]}
     * JSON-PRC Response: {"result":"cT2nq6H4aYurfvbYS9nK9LgqWeg3hRK4XirANMccZ8iEeQgzcaCe","error":null,"id":"1604073600"}
     */
    @Test
    public void dumpprivkey() {
        methodName = "dumpprivkey";
        argument = new Object[]{"2N2q9imkFkp6EjP6GovH5ytabBURUrEbMw3"};
        clazz = Object.class;
    }

    /**
     * 获取事务列表
     * Request {"id":"1704965645","jsonrpc":"2.0","method":"listunspent"}
     * JSON-PRC Response: {"result":[{"txid":"3ce30f7cb0452105aaafad17b4dac46dc0860e83b97fd6f74368735c61fabed1","vout":0,"address":"2N2q9imkFkp6EjP6GovH5ytabBURUrEbMw3","label":"test_user_1","account":"test_user_1","redeemScript":"00148fba0f599ad91529ac0bc5396cc37e29d40323b0","scriptPubKey":"a9146923b664a7392344b81f250158c3b192b07b3a7587","amount":0.01,"confirmations":5,"spendable":true,"solvable":true,"safe":true}],"error":null,"id":"303362890"}
     */
    @Test
    public void listunspent() {
        methodName = "listunspent";
        argument = new Object[]{};
        clazz = Object.class;
    }

    /**
     * 创建交易原串
     * Request {"id":"106217390","jsonrpc":"2.0","method":"createrawtransaction","params":[[{"txid":"3ce30f7cb0452105aaafad17b4dac46dc0860e83b97fd6f74368735c61fabed1","vout":1}],{"2NADEYUqBcKziLfdRrafsoedFm1gxfdpd6D":0.001}]}
     * JSON-PRC Response: {"result":"0200000001d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c0100000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000","error":null,"id":"106217390"}
     */
    @Test
    public void createrawtransaction() {
        methodName = "createrawtransaction";
        Map<String, Object> tx = new HashMap<>();
        tx.put("txid", "3ce30f7cb0452105aaafad17b4dac46dc0860e83b97fd6f74368735c61fabed1");
        tx.put("vout", 0);
        Map<String, Object> address = new HashMap<>();
        address.put("2NADEYUqBcKziLfdRrafsoedFm1gxfdpd6D", 0.001);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(tx);
        argument = new Object[]{list, address};
        clazz = Object.class;
    }

    /**
     * 签名交易串
     * Request {"id":"920622720","jsonrpc":"2.0","method":"signrawtransaction","params":["0200000001f64ea5001d9c33919645d9b30d6313c77eb2720bb5281998da45c5439cf0d3fd0100000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000"]}
     * JSON-PRC Response: {"result":{"hex":"0200000001f64ea5001d9c33919645d9b30d6313c77eb2720bb5281998da45c5439cf0d3fd0100000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000","complete":false,"errors":[{"txid":"fdd3f09c43c545da981928b50b72b27ec713630db3d9459691339c1d00a54ef6","vout":1,"witness":[],"scriptSig":"","sequence":4294967295,"error":"Input not found or already spent"}]},"error":null,"id":"920622720"}
     */
    @Test
    public void signrawtransaction() {
        methodName = "signrawtransaction";
        argument = new Object[]{"0200000001d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c0100000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000"};
        clazz = Object.class;
    }

    /**
     * 使用私钥签名交易串
     * Request {"id":"1804105801","jsonrpc":"2.0","method":"signrawtransactionwithkey","params":["0200000001d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c0000000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000",["cT2nq6H4aYurfvbYS9nK9LgqWeg3hRK4XirANMccZ8iEeQgzcaCe"]]}
     * JSON-PRC Response: {"result":{"hex":"02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000","complete":true},"error":null,"id":"1804105801"}
     */
    @Test
    public void signrawtransactionwithkey() {
        methodName = "signrawtransactionwithkey";
        argument = new Object[]{"0200000001d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c0000000000ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada15958700000000", new String[]{"cT2nq6H4aYurfvbYS9nK9LgqWeg3hRK4XirANMccZ8iEeQgzcaCe"}};
        clazz = Object.class;
    }

    /**
     * 广播交易
     * Request {"id":"497874801","jsonrpc":"2.0","method":"sendrawtransaction","params":["02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000"]}
     * JSON-PRC Response: {"result":"c054b29f985d506fad287c5a0531932992254dec053c44790df2e026aa649a89","error":null,"id":"497874801"}
     */
    @Test
    public void sendrawtransaction() {
        methodName = "sendrawtransaction";
        argument = new Object[]{"02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000"};
        clazz = Object.class;
    }

    /**
     * 获取交易信息
     * https://live.blockcypher.com/btc-testnet/tx/c054b29f985d506fad287c5a0531932992254dec053c44790df2e026aa649a89/
     * Request {"id":"337988647","jsonrpc":"2.0","method":"getrawtransaction","params":["c054b29f985d506fad287c5a0531932992254dec053c44790df2e026aa649a89"]}
     * JSON-PRC Response: {"result":"02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000","error":null,"id":"337988647"}
     */
    @Test
    public void getrawtransaction() {
        methodName = "getrawtransaction";
        argument = new Object[]{"c054b29f985d506fad287c5a0531932992254dec053c44790df2e026aa649a89"};
        clazz = Object.class;
    }

    /**
     * 获取交易信息
     * Request {"id":"352885087","jsonrpc":"2.0","method":"decoderawtransaction","params":["02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000"]}
     * JSON-PRC Response: {"result":{"txid":"c054b29f985d506fad287c5a0531932992254dec053c44790df2e026aa649a89","hash":"3add3e85e405535e5dee93921b682ffe01f1a423d7147aa91b5e6b62371ea329","version":2,"size":215,"vsize":134,"weight":533,"locktime":0,"vin":[{"txid":"3ce30f7cb0452105aaafad17b4dac46dc0860e83b97fd6f74368735c61fabed1","vout":0,"scriptSig":{"asm":"00148fba0f599ad91529ac0bc5396cc37e29d40323b0","hex":"1600148fba0f599ad91529ac0bc5396cc37e29d40323b0"},"txinwitness":["304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a1975301","028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d"],"sequence":4294967295}],"vout":[{"value":0.001,"n":0,"scriptPubKey":{"asm":"OP_HASH160 ba19c510d04d7ceda50d7bfb665833ab5ada1595 OP_EQUAL","hex":"a914ba19c510d04d7ceda50d7bfb665833ab5ada159587","reqSigs":1,"type":"scripthash","addresses":["2NADEYUqBcKziLfdRrafsoedFm1gxfdpd6D"]}}]},"error":null,"id":"352885087"}
     */
    @Test
    public void decoderawtransaction() {
        methodName = "decoderawtransaction";
        argument = new Object[]{"02000000000101d1befa615c736843f7d67fb9830e86c06dc4dab417adafaa052145b07c0fe33c00000000171600148fba0f599ad91529ac0bc5396cc37e29d40323b0ffffffff01a08601000000000017a914ba19c510d04d7ceda50d7bfb665833ab5ada1595870247304402204bb6c791309024be09e819813595d417940fab3f2108d7988e3c59051e6c454c02200bf5949822ff14cf6c8b6f41e2796a1a28b499436d69bdec23e646e009a197530121028273ba1a2f8c5d60016fcf3501e7417b4ce0711f2b5706ca9e2b5be5c745918d00000000"};
        clazz = Object.class;
    }
}
