package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.repository.CryptoCoinRepository;
import com.my.demo.wallet.service.CryptoCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CryptoCoinServiceImpl implements CryptoCoinService {

    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;


}
