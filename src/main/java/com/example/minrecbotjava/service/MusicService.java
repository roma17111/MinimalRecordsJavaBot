package com.example.minrecbotjava.service;

import com.example.minrecbotjava.entity.MusicInfo;

public interface MusicService {
    MusicInfo getActiveServiceByChatId(long chatId);
}
