package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.entity.UserInfo;
import com.example.minrecbotjava.repository.UserInfoRepository;
import com.example.minrecbotjava.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userRepository;

    @Override
    public UserInfo setRoleToUserById(boolean isAdmin, long id) {
        UserInfo userInfo = userRepository.findById(id).orElse(null);
        assert userInfo != null;
        userInfo.setAdmin(isAdmin);
        userRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public List<UserInfo> findAll() {
        return userRepository.findAll();
    }
}
