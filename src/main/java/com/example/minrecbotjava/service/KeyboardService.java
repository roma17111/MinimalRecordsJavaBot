package com.example.minrecbotjava.service;

import com.example.minrecbotjava.entity.UserInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface KeyboardService {
    void getMenu(Update update,
                 String text,
                 UserInfo user);
}
