package com.my.demo.wallet.repository;

import com.my.demo.wallet.entity.CryptoAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoAddressRepository extends JpaRepository<CryptoAddress, Long>  {
}
