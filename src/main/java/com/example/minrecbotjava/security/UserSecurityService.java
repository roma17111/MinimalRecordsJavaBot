package com.example.minrecbotjava.security;

public interface UserSecurityService {

    boolean login(String userName, String password);


    boolean register(String userName, String password, SecurityRole role);
}
