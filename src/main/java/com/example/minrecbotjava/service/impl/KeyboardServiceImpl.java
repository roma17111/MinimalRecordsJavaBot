package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.entity.UserInfo;
import com.example.minrecbotjava.service.KeyboardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class KeyboardServiceImpl implements KeyboardService {

    public static final String USERS = "\"\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB Список пользователей\"";
    public static final String SERVICES_MUSIC = "🧰 Список заявок";
    public static final String OPPORTUNITIES = "📣 Наши возможности";
    public static final String CREATE_MUSIC_SERVICE = "📱 Заказать услугу";
    public static final String WEB = "🌍 Наш сайт";
    public static final String WEB_URL = "https://roma17111.github.io/index.html";
    public static final String CONTACTS = "🗨️ Контакты";

    private final TelegramLongPollingBot bot;

    @Override
    public void getMenu(Update update,
                        String text,
                        UserInfo user) {
        SendMessage message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(update.getMessage().getChatId());
        message.setText(text);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        if (user.isAdmin()) {
            KeyboardRow row1 = new KeyboardRow();
            row1.add(USERS);
            row1.add(SERVICES_MUSIC);
            rows.add(row1);
        }
        KeyboardRow row2 = new KeyboardRow();
        row2.add(OPPORTUNITIES);
        row2.add(CREATE_MUSIC_SERVICE);
        rows.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton web = new KeyboardButton();
        web.setText(WEB);
        web.setWebApp(new WebAppInfo(WEB_URL));
        row3.add(web);
        row3.add(CONTACTS);
        rows.add(row3);
        KeyboardRow row4 = new KeyboardRow();
        markup.setKeyboard(rows);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }
}
