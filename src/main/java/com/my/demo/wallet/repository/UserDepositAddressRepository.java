package com.my.demo.wallet.repository;

import com.my.demo.wallet.entity.UserDepositAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepositAddressRepository extends JpaRepository<UserDepositAddress, Integer> {
}
