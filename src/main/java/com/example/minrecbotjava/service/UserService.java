package com.example.minrecbotjava.service;

import com.example.minrecbotjava.entity.UserInfo;

import java.util.List;

public interface UserService {
    UserInfo setRoleToUserById(boolean isAdmin, long id);

    List<UserInfo> findAll();
}
