package com.my.demo.wallet.repository;

import com.my.demo.wallet.Application;
import com.my.demo.wallet.entity.CryptoCoin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class CryptoCoinRepositoryTest {
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    @Test
    public void testSave() {
        CryptoCoin record = CryptoCoin.builder()
                .name("Ether")
                .symbol("ETH")
                .contract("")
                .ticker(BigDecimal.ZERO)
                .decimals(18)
                .confirm(30)
                .gasPrice(BigInteger.valueOf(15_000_000_000L))
                .gasLimit(BigInteger.valueOf(21_000L))
                .type(CryptoCoin.CoinType.ERC20.getType())
                .status(CryptoCoin.CoinStatus.SHOW.getStatus())
                .createTime(new Date())
                .modifyTime(new Date())
                .isDelete(false)
                .build();
        cryptoCoinRepository.save(record);
    }

    @Test
    public void testFindList() {
        Example<CryptoCoin> example = Example.of(CryptoCoin.builder().isDelete(false).build());
        List<CryptoCoin> coinList = cryptoCoinRepository.findAll(example);
        System.out.println(coinList);
    }

    @Test
    public void testGetOne() {
        CryptoCoin coin = cryptoCoinRepository.getOne(1L);
        System.out.println(coin);
    }
}
