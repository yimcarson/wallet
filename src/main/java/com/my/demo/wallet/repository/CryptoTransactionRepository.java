package com.my.demo.wallet.repository;

import com.my.demo.wallet.entity.CryptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Long> {
}
