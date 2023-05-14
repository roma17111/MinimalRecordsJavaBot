package com.example.minrecbotjava.controllers;

import com.example.minrecbotjava.config.BotConfig;
import com.example.minrecbotjava.entity.MusicInfo;
import com.example.minrecbotjava.entity.UserInfo;
import com.example.minrecbotjava.repository.UserInfoRepository;
import com.example.minrecbotjava.service.KeyboardService;
import com.example.minrecbotjava.service.MusicService;
import com.example.minrecbotjava.service.impl.KeyboardServiceImpl;
import com.example.minrecbotjava.service.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


@Service
@Slf4j
@RequiredArgsConstructor
public class BotStart extends TelegramLongPollingBot {

    private final MusicService musicService;
    private final UserInfoRepository userInfoRepository;
    private final BotConfig botConfig;
    private final KeyboardService keyboardService = new KeyboardServiceImpl(this);

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/start" -> {
                    getStart(update);
                    break;
                }
                default -> {


                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    private boolean isUserAdmin(Update update) {
        long chatId = update.getMessage().getChatId();
        return userInfoRepository.findByChatId(chatId).isAdmin();
    }

    private boolean isHasActualService(Update update) {
        return musicService.getActiveServiceByChatId(update.getMessage().getChatId()) != null;
    }

    private MusicInfo getActualMusicInfo(Update update) {
        return musicService.getActiveServiceByChatId(update.getMessage().getChatId());
    }

    private void createMusicService(Update update) {


    }

    private void registerUser(Update update) {
        UserInfo user = new UserInfo();
        user.setChatId(update.getMessage().getChatId());
        user.setFirstName(update.getMessage().getChat().getFirstName());
        user.setLastName(update.getMessage().getChat().getLastName());
        user.setNickName(update.getMessage().getChat().getUserName());
        user.setAdmin(false);
        userInfoRepository.save(user);
    }

    private void getStart(Update update) {
        SendPhoto photo = new SendPhoto();
        photo.setParseMode("html");
        photo.setChatId(update.getMessage().getChatId());
        photo.setPhoto(new InputFile(new File(TextUtil.START_PHOTO_PATH)));
        photo.setCaption(TextUtil.START);
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        UserInfo userInfo = userInfoRepository.findByChatId(update.getMessage().getChatId());
        if (userInfo == null) {
            registerUser(update);
        }
        keyboardService.getMenu(update,"\uD83D\uDD79️ Выберите действие",userInfo);
    }


    private void sendMessage(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.enableHtml(true);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
