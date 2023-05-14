package com.example.minrecbotjava.controllers;

import com.example.minrecbotjava.config.BotConfig;
import com.example.minrecbotjava.entity.MusicInfo;
import com.example.minrecbotjava.entity.UserInfo;
import com.example.minrecbotjava.repository.UserInfoRepository;
import com.example.minrecbotjava.service.KeyboardService;
import com.example.minrecbotjava.service.MusicEmailService;
import com.example.minrecbotjava.service.MusicService;
import com.example.minrecbotjava.service.ValidatorService;
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
import java.util.List;

import static com.example.minrecbotjava.service.impl.KeyboardServiceImpl.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class BotStart extends TelegramLongPollingBot {

    private final MusicService musicService;
    private final UserInfoRepository userInfoRepository;
    private final BotConfig botConfig;
    private final KeyboardService keyboardService = new KeyboardServiceImpl(this);
    private final ValidatorService validatorService;
    private final MusicEmailService emailService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/start" -> {
                    if (getActualMusicInfo(update) == null) {

                        getStart(update);
                    }
                    break;
                }
                case CANCEL_MUSIC_SERVICE -> {
                    musicService.deleteMusicService(getActualMusicInfo(update));
                    keyboardService.getMenu(update, "❌✅ Вы отменили заявку❗",
                            userInfoRepository.findByChatId(update.getMessage().getChatId()));
                    break;
                }
                case USERS -> {
                    if (getActualMusicInfo(update) == null) {

                        getAllUsers(update);
                    }
                    break;
                }
                case SERVICES_MUSIC -> {
                    if (getActualMusicInfo(update) == null) {
                        getAllMusicServices(update);
                    }
                    break;
                }
                case OPPORTUNITIES -> {
                    if (getActualMusicInfo(update) == null) {
                        sendMessage(update, TextUtil.OPPORTUNITIES);
                    }
                    break;
                }
                case CREATE_MUSIC_SERVICE -> {
                    if (getActualMusicInfo(update) == null) {
                        createMusicService(update);
                    }
                    break;
                }
                case CONTACTS -> {
                    if (getActualMusicInfo(update) == null) {
                        sendMessage(update, TextUtil.INFO);
                    }
                    break;
                }
                default -> {
                    System.out.println(getActualMusicInfo(update));
                    if (getActualMusicInfo(update) != null) {
                        generateMusicInfo(update);
                        break;
                    } else {
                        keyboardService.getMenu(update,
                                "\uD83D\uDD79️ Выберите действие",
                                userInfoRepository.findByChatId(update.getMessage().getChatId()));
                    }
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
        if (getActualMusicInfo(update) != null) {
            keyboardService.getMenu(update, "⛔ Вы уже отправляете заявку\n " +
                    "\uD83D\uDD79️ Выберите действие", userInfoRepository.findByChatId(update.getMessage().getChatId()));
            return;
        }
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.setChatId(update.getMessage().getChatId());
        musicInfo.setActive(true);
        musicInfo.setStateId(1);
        musicService.save(musicInfo);
        keyboardService.getCancelKeyboard(update, "Вы собираетесь заказать услугу\n" +
                "\uD83E\uDD1D  Представьтесь");
    }

    private void generateMusicInfo(Update update) {
        String text = update.getMessage().getText();
        MusicInfo musicInfo = getActualMusicInfo(update);
        switch (musicInfo.getStateId()) {
            case 1:
                if (!validatorService.isValidFio(text)) {
                    sendMessage(update, "❌❗⚠️ Введены некорректные данные\n\n" +
                            "👩🏻‍💻 Представьтесь");
                    break;
                }
                musicInfo.setStateId(2);
                musicInfo.setFio(text);
                musicService.save(musicInfo);
                sendMessage(update, "☎️  Укажите телефон");
                break;
            case 2:
                if (!validatorService.isValidPhoneNumber(text)) {
                    sendMessage(update, "❌❗⚠️ Вы указали некорректный номер телефона\n\n" +
                            "☎️  Укажите телефон");
                    break;
                }
                musicInfo.setPhone(text);
                musicInfo.setStateId(3);
                musicService.save(musicInfo);
                sendMessage(update, "📧  Укажите email");
                break;
            case 3:
                if (!validatorService.isValidEmail(text)) {
                    sendMessage(update, "❌❗⚠️ Некорректный email адрес\n\n" +
                            "📧  Введите email");
                    break;
                }
                musicInfo.setStateId(4);
                musicInfo.setEmail(text);
                musicService.save(musicInfo);
                sendMessage(update, "🧾  Опишите услугу");
                break;
            case 4:
                musicInfo.setDescription(text);
                musicInfo.setStateId(5);
                musicInfo.setActive(false);
                musicService.save(musicInfo);
                keyboardService.getMenu(update, "✔️  Заявка на оказание услуги отправлена\n" +
                                "👍  с вами свяжутся в ближайшее время\n" +
                                "\uD83D\uDCD1 Ваша заявка: \n"+musicInfo.toString(),
                        userInfoRepository.findByChatId(update.getMessage().getChatId()));
                emailService.sendSimpleEmail("romanze1706@gmail.com",
                        "MinimalRecords", musicInfo.toString());
                break;
        }

    }

    public void getAllUsers(Update update) {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        for (UserInfo info : userInfoList) {
            sendMessage(update, info.toString());
        }
    }

    public void getAllMusicServices(Update update) {
        for (MusicInfo musicInfo : musicService.findAll()) {
            sendMessage(update, musicInfo.toString());
        }
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
        keyboardService.getMenu(update, "\uD83D\uDD79️ Выберите действие", userInfoRepository.findByChatId(update.getMessage().getChatId()));
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
