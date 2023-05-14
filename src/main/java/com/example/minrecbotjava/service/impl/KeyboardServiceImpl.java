package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.service.KeyboardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class KeyboardServiceImpl implements KeyboardService {

    public static final String USERS = "\"\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB Список пользователей\"";
    public static final String SERVICES_MUSIC = "🧰 Список заявок";
    public static final String opportunities = "📣 Наши возможности";
    public static final String CREATE_MUSIC_SERVICE = "📱 Заказать услугу";
    public static final String WEB = "🌍 Наш сайт";
    public static final String WEB_URL = "https://roma17111.github.io/index.html";
    public static final String CONTACTS = "🗨️ Контакты";

    private final TelegramLongPollingBot bot;

    @Override
    public void getMenu(Update update, String text) {

        SendMessage message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(update.getMessage().getChatId());
        message.setText(text);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

    }
}
