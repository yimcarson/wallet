package com.my.demo.wallet.repository;

import com.my.demo.wallet.entity.UserDepositRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepositRecordRepository extends JpaRepository<UserDepositRecord, Integer> {
}
