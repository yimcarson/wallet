package com.my.demo.wallet.service.impl;

import com.my.demo.wallet.repository.UserInfoRepository;
import com.my.demo.wallet.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
}
