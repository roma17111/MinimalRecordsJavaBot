package com.example.minrecbotjava.repository;

import com.example.minrecbotjava.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findByChatId(long chatId);
}
