package com.my.demo.wallet.repository;

import com.my.demo.wallet.Application;
import com.my.demo.wallet.entity.UserDepositAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class UserDepositAddressRepositoryTest {

    @Autowired
    private UserDepositAddressRepository userDepositAddressRepository;

    @Test
    public void testCount() {
        long counter = userDepositAddressRepository.count(Example.of(UserDepositAddress.builder().address("0xf87ea8914f849ab51c452dfc91ea7a74a1c4fdcc").build()));
        System.out.println(counter);
    }
}