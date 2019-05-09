package com.my.demo.wallet.service;

import com.my.demo.wallet.entity.CryptoAddress;

import java.util.List;

public interface CryptoAddressService {
    List<CryptoAddress> findList(CryptoAddress record);
}
