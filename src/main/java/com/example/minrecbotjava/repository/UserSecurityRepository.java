package com.example.minrecbotjava.repository;

import com.example.minrecbotjava.security.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity,Long> {

    UserSecurity findByUserName(String userName);
}
