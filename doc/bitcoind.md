# Bitcoin RPC
>version 0.17

```bash
bitcoind -datadir=/root/.bitcoin -conf=/root/.bitcoin/bitcoin.conf -daemon
```

### 查看账户余额

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "getbalance","params": [],"id": 0}'
```

### 新增一个帐号

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "getnewaddress","params": ["test001"],"id": 0}'
```

### 获取账户

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "getaddressesbyaccount","params": ["test001"],"id": 0}'
```

>2MvMU9RBQV3UFGGCHSfr1MXv5JZQwX4PWx6

### 检查未使用的事务列表

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "listunspent","params": [],"id": 0}'
```

### 创建一个转账事务

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "createrawtransaction","params": [[{"txid": "0ca32dc3dd464ef3b33745ff20321b825d7660fe09b2431583b68d8b3c8297b2","vout": 0} ],{"2Mu6Pyj6X5pMvqko4gH81JQna9To6d7Gqd9": 0.01 } ],"id": 0}'
```

>{"result":"0200000001b297823c8b8db6831543b209fe60765d821b3220ff4537b3f34e46ddc32da30c0000000000ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c8700000000","error":null,"id":0}

### 解锁钱包

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "walletpassphrase","params": ["123456", 1000],"id": 0}'
```

### 用钱包签名

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "signrawtransactionwithwallet","params": ["020000000169a2409026b5670e2d745bcd41bafbc5251783ff8f3723e05d811268f3d91cec0000000000ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c8700000000"],"id": 0}'
```

>{"result":{"hex":"0200000000010169a2409026b5670e2d745bcd41bafbc5251783ff8f3723e05d811268f3d91cec0000000017160014bb61df7aaa58e508f31db232a0f9b848fa971407ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c87024730440220532d1ce301d7409dfea80fcddf4a5323842c8aab0bf03bafe08a2fd42706806e0220565f3380d240dbca4b2631a76128257a7eeb221f0cfd21853c42f875f34c98e4012103f10bfba06954a26c6887789d442a4f874d096f418f7b33a16dcba9385306500b00000000","complete":true},"error":null,"id":0}

### 获取私钥

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "dumpprivkey","params": ["2MvMU9RBQV3UFGGCHSfr1MXv5JZQwX4PWx6"],"id": 0}'
```

>{"result":"cQmQ1Kuk9GvbHeVyzxvTtcUd1KHe2i5HFdukQvXdg5irDgVkEKaK","error":null,"id":0}

### 用私钥签名

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "signrawtransactionwithkey","params": ["0200000001b297823c8b8db6831543b209fe60765d821b3220ff4537b3f34e46ddc32da30c0000000000ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c8700000000", ["cQmQ1Kuk9GvbHeVyzxvTtcUd1KHe2i5HFdukQvXdg5irDgVkEKaK"]],"id": 0}'
```

### 广播签名交易串

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "sendrawtransaction","params": ["0200000000010169a2409026b5670e2d745bcd41bafbc5251783ff8f3723e05d811268f3d91cec0000000017160014bb61df7aaa58e508f31db232a0f9b848fa971407ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c87024730440220532d1ce301d7409dfea80fcddf4a5323842c8aab0bf03bafe08a2fd42706806e0220565f3380d240dbca4b2631a76128257a7eeb221f0cfd21853c42f875f34c98e4012103f10bfba06954a26c6887789d442a4f874d096f418f7b33a16dcba9385306500b00000000"],"id": 0}'
```

>{"result":"fdba94e6b8dc364d36453fa2eab336ae00f7aebfbb716c9da38f1bac4ce700ec","error":null,"id":0}

### 获取交易信息

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "getrawtransaction","params": ["fdba94e6b8dc364d36453fa2eab336ae00f7aebfbb716c9da38f1bac4ce700ec"],"id": 0}'
```

### 解析交易信息

```bash
curl -X POST -H 'Content-Type: application/json' --user test:123456 http://192.168.1.201:18332 --data '{"jsonrpc": "1.0","method": "decoderawtransaction","params": ["0200000000010169a2409026b5670e2d745bcd41bafbc5251783ff8f3723e05d811268f3d91cec0000000017160014bb61df7aaa58e508f31db232a0f9b848fa971407ffffffff0140420f000000000017a9141444d8f3c0c0f852ee6efaf653ef059966b00b4c87024730440220532d1ce301d7409dfea80fcddf4a5323842c8aab0bf03bafe08a2fd42706806e0220565f3380d240dbca4b2631a76128257a7eeb221f0cfd21853c42f875f34c98e4012103f10bfba06954a26c6887789d442a4f874d096f418f7b33a16dcba9385306500b00000000"],"id": 0}'
```