package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.entity.MusicInfo;
import com.example.minrecbotjava.repository.MusicInfoRepository;
import com.example.minrecbotjava.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicServiceImpl implements MusicService {

    private final MusicInfoRepository musicInfoRepository;

    @Override
    public MusicInfo getActiveServiceByChatId(long chatId) {
        return musicInfoRepository.findByChatIdIsActive(chatId);
    }


}
