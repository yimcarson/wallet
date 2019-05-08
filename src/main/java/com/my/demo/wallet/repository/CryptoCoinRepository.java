package com.my.demo.wallet.repository;

import com.my.demo.wallet.entity.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, Long> {
}
