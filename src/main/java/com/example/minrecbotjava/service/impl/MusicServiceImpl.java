package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.entity.MusicInfo;
import com.example.minrecbotjava.repository.MusicInfoRepository;
import com.example.minrecbotjava.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicServiceImpl implements MusicService {

    private final MusicInfoRepository musicInfoRepository;

    @Override
    public void save(MusicInfo musicInfo) {
        musicInfoRepository.save(musicInfo);
    }

    @Override
    public MusicInfo getActiveServiceByChatId(long chatId) {
        return musicInfoRepository.findByChatIdIsActive(chatId);
    }

    @Override
    public void deleteMusicService(MusicInfo musicInfo) {
        musicInfoRepository.delete(musicInfo);
    }

    @Override
    public List<MusicInfo> findAll() {
        return musicInfoRepository.findAll();
    }

    @Override
    public void deleteMusicServiceById(long id) {
        musicInfoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        musicInfoRepository.deleteAll();
    }

    @Override
    public MusicInfo findById(long id) {
        return musicInfoRepository.findById(id).orElse(null);
    }

}
