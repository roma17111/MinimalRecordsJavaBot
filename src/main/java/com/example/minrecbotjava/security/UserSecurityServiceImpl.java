package com.example.minrecbotjava.security;

import com.example.minrecbotjava.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserSecurityRepository securityRepository;
    private final PasswordEncoder encoder;
    private final CustomUserDetailsService customUserDetailsService;


    public UserSecurityServiceImpl(UserSecurityRepository securityRepository, PasswordEncoder encoder, CustomUserDetailsService customUserDetailsService) {
        this.securityRepository = securityRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (securityRepository.findByUserName(userName) == null) {
            return false;
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        return encoder.matches(password, encryptedPassword);
    }

    @Override
    public boolean register(String userName,String password,SecurityRole role) {
        if (securityRepository.findByUserName(userName) != null) {
            return false;
        }
        UserSecurity user = new UserSecurity();
        user.setPassword(encoder.encode(password));
        user.setUserName(userName);
        user.setRole(role);
        securityRepository.save(user);
        log.info("User registered! " + user);
        return true;
    }

}
