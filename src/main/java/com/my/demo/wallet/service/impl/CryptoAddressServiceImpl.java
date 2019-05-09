package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.entity.CryptoAddress;
import com.my.demo.wallet.repository.CryptoAddressRepository;
import com.my.demo.wallet.service.CryptoAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CryptoAddressServiceImpl implements CryptoAddressService {
    @Autowired
    private CryptoAddressRepository cryptoAddressRepository;

    @Override
    public List<CryptoAddress> findList(CryptoAddress record) {
        return cryptoAddressRepository.findAll(Example.of(record));
    }
}
