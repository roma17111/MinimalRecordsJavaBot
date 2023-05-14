package com.example.minrecbotjava.service;

import com.example.minrecbotjava.entity.MusicInfo;

import java.util.List;

public interface MusicService {


    void save(MusicInfo musicInfo);

    MusicInfo getActiveServiceByChatId(long chatId);

    void deleteMusicService(MusicInfo musicInfo);

    List<MusicInfo> findAll();

    void deleteMusicServiceById(long id);

    void deleteAll();

    MusicInfo findById(long id);
}
