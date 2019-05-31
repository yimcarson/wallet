# GETH

## 安装

### Ubuntu安装geth

* 安装依赖

```bash
apt-get install software-properties-common python-software-properties
```

* 获取源

```bash
add-apt-repository -y ppa:ethereum/ethereum
```

* 安装

```bash
apt-get update
```

```bash
apt-get install ethereum
```

### Cnetos安装geth

* 下载源码

```bash
git clone https://github.com/ethereum/go-ethereum.git
```

* 选择版本

```bash
git checkout v1.7.2
```

* 安装go环境

```bash
wget https://www.golangtc.com/static/go/1.9.2/go1.9.2.linux-amd64.tar.gz
```

```bash
tar -xzf  go1.9.2.linux-amd64.tar.gz -C /usr/local/
```
```bash
vim /etc/profile
```

```text
export GOPATH=/usr/local/go
export PATH=$GOPATH/bin:$PATH
```

```bash
source /etc/profile
```

* 检查go环境

```bash
go version
```

输出如`go version go1.9.2 linux/amd64`即安装成功

* 编译go-ethereum

cd至go-ethereum目录，使用make命令进行build

```bash
make geth
```

```bash
make all
```

* 为geth配置环境变量

```text
vim /etc/profile
```

```text
export GETH=/data/git/go-ethereum/build
export PATH=$GETH/bin:$PATH
```

`/data/git`是`git clone`所在的地址

```bash
source /etc/profile
```

## 启动

### prod

```bash
nohup geth --rpc --rpcport=47895 --rpcaddr=`hostname -I` --rpcapi=eth,web3,admin,personal,net,miner,txpool,debug --datadir=/data --keystore=$HOME/.ethereum/keystore > $HOME/geth.out &
```

### Ropsten网络

```bash
nohup geth --rpc --rpcport 8545 --rpcaddr=`hostname -I` --rpcapi eth,web3,admin,personal,net,miner,txpool,debug --datadir=$HOME/.ropsten --testnet --bootnodes "enode://4ff542002eb13116edcbd6021d7e4fd79e5c95937f45cfb3e9f259442aa50831216d1752d3325393457fad48b74792a872853088b98d5ded0845fbaafdc7eb4a@47.75.177.252:30303" > $HOME/geth.out & 
```

```bash
nohup geth --rpc --rpcport 8545 --rpcaddr=`hostname -I` --rpcapi eth,web3,admin,personal,net,miner,txpool,debug --datadir=$HOME/.ropsten --testnet > $HOME/geth.out &
```

### mining

```bash
nohup geth --rpc --rpcport 8545 --rpcaddr=`hostname -I` --rpcapi eth,web3,admin,personal,net,miner,txpool,debug --datadir=$HOME/.geth --bootnodes "enode://85ecdb3820a5cfed82b89f725c71855a4e0aafc5fce6b251baf67d8e5262e08e9d713082ef5e6ca70710fb682216f3083c9e2f58784258524fda3041a431185e@192.168.1.200:31313,enode://6332792c4a00e3e4ee0926ed89e0d27ef985424d97b6a45bf0f23e51f0dcb5e66b875777506458aea7af6f9e4ffb69f43f3778ee73c81ed9d34c51c4b16b0b0f@52.232.243.152:30303,enode://94c15d1b9e2fe7ce56e458b9a3b672ef11894ddedd0c6f247e0f1d3487f52b66208fb4aeb8179fce6e3a749ea93ed147c37976d67af557508d199d9594c35f09@192.81.208.223:30303" > $HOME/geth.out & 
```

```bash
nohup geth --rpc --rpcport 8545 --rpcaddr=`hostname -I` --rpcapi eth,web3,admin,personal,net,miner,txpool,debug --datadir=$HOME/.geth > $HOME/geth.out &
```


### 私有链启动

* 私有连创世区块配置

```bash
vim $HOME/.private/genesis.json
```

```json
{
	"nonce":"0x0000000000000042",
	"mixhash":"0x0000000000000000000000000000000000000000000000000000000000000000",
	"difficulty": "0x4000",
	"alloc": {},
	"coinbase":"0x0000000000000000000000000000000000000000",
	"timestamp": "0x00",
	"parentHash":"0x0000000000000000000000000000000000000000000000000000000000000000",
	"extraData": "0x00000000",
	"gasLimit":"0xffffffff",
	"config":{
		"chainId": 666,
		"homesteadBlock": 0,
		"eip155Block": 0,
		"eip158Block": 0
	}
}
```

* 创世区块的初始化

```bash
geth init $HOME/.private/genesis.json --datadir $HOME/.private
```

* 启动

```bash
nohup geth --rpc --rpcport 8545 --rpcaddr=`hostname -I`--rpcapi eth,web3,admin,personal,net,miner,txpool,debug --datadir=$HOME/.ropsten --networkid 3  > $HOME/geth.out & 
```

## 操作

* 输出 `nohup`日志

```bash
tail -f $HOME/geth.out
```

* 打开控制台

```bash
geth attach http://localhost:8545
```

>`--rpcaddr`适用`hostname -I`时，`localhost`替换为ip地址。