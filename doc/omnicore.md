# omnicore

## 安装omnicore

### 下载程序包

* 下载

前往[https://github.com/OmniLayer/omnicore/releases](https://github.com/OmniLayer/omnicore/releases)下载需要的程序包。下面是为Ubuntu环境安装最新的omnicore需要的程序压缩包。

```bash
wget https://github.com/OmniLayer/omnicore/releases/download/v0.5.0/omnicore-0.5.0-x86_64-linux-gnu.tar.gz
```

* 解压缩

```bush
tar -zxvf omnicore-0.5.0-x86_64-linux-gnu.tar.gz
```

* 移动程序文件到`/usr/local`下

```bash
mv omnicore-0.5.0 /usr/local/omnicore
```

>这是我的个人习惯，喜欢放哪就放哪

* 添加软连接

为`bin`下的可执行文件添加软连接到`/usr/local/bin`

```bash
ln -s /usr/local/omnicore/bin/omnicored /usr/local/bin/omnicored
```

```bash
ln -s /usr/local/omnicore/bin/omnicore-cli /usr/local/bin/omnicore-cli
```

只添加这两个就够了。

### 配置

```bash
vim /root/.omnicore/bitcoin.conf
```

这是必要的配置信息

```text
# 测试网络为1，主网络为0
testnet=1
# 是否启用RPC客户端
server=1
# RPC客户端的用户名
rpcuser=test
# RPC客户端的密码
rpcpassword=123456
# RPC客户端的访问权限，这样写的意思是允许所有访问
rpcallowip=0.0.0.0/0
# RPC客户端的监听端口
rpcport=8332
# 与bitcoind的不同之处，omnicored必须指定这一项，否则启动会提示错误
txindex=1
```

### 启动

```bash
omnicored -datadir=/root/.omnicore -conf=/root/.omnicore/bitcoin.conf -daemon
```

输出

```text
2019-07-03 01:47:44 Initializing Omni Core v0.5.0 [test]
2019-07-03 01:47:44 Loading trades database: OK
2019-07-03 01:47:44 Loading send-to-owners database: OK
2019-07-03 01:47:44 Loading tx meta-info database: OK
2019-07-03 01:47:44 Loading smart property database: OK
2019-07-03 01:47:44 Loading master transactions database: OK
2019-07-03 01:47:44 Loading fee cache database: OK
2019-07-03 01:47:44 Loading fee history database: OK
2019-07-03 01:47:44 Loading persistent state: NONE (no usable previous state found)
2019-07-03 01:47:44 Omni Core initialization completed
```

键入回车退出输出面板

检查一下

```bash
curl -H 'Content-Type:application/json' -X POST --data '{"id":"1","jsonrpc":"2.0","method":"getwalletinfo"}' --user test http://127.0.0.1:8332
```

```text
{"result":{"walletversion":130000,"balance":0.00000000,"unconfirmed_balance":0.00000000,"immature_balance":0.00000000,"txcount":0,"keypoololdest":1562118464,"keypoolsize":100,"paytxfee":0.00000000,"hdmasterkeyid":"a57dade76912a83c1757ad8df4908b2921a56877"},"error":null,"id":"1"}
```

同步区块数据需要一段时间，可以持续输出日志查看进度

```bash
tail -f /root/.omnicore/testnet3/debug.log 
```

```text
……
2019-07-03 02:22:49 UpdateTip: new best=0000000002126ac042234048f10fc5a07875a4b8e17ebb149433fa5f81e077aa height=337439 version=0x00000003 log2_work=65.922758 tx=3520231 date='2015-04-13 21:06:42' progress=0.883869 cache=99.3MiB(504706tx)
2019-07-03 02:22:49 UpdateTip: new best=0000000000f2d9d4b54b27e8708cac2387e4abc1dc99ca63446aecaba681d387 height=337440 version=0x00000003 log2_work=65.922758 tx=3520232 date='2015-04-13 21:06:37' progress=0.883869 cache=99.3MiB(504707tx)
……
```