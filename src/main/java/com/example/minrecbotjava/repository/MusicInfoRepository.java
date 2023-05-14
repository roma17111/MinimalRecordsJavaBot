package com.example.minrecbotjava.repository;

import com.example.minrecbotjava.entity.MusicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicInfoRepository extends JpaRepository<MusicInfo,Long> {

    @Query(value = "select * from  service  where is_active = true and chat_id = :chatId ",nativeQuery = true)
    MusicInfo findByChatIdIsActive(@Param("chatId") long chatId);
}
