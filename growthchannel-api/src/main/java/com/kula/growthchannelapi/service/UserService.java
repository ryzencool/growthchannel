package com.kula.growthchannelapi.service;

import com.kula.growthchannelapi.domain.UserInfo;
import com.kula.growthchannelapi.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;

    public UserService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public UserInfo userInfo(Long id) {
        return userInfoRepository.findById(id).orElseThrow();
    }
}
