package com.example.minrecbotjava.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface KeyboardService {
    void getMenu(Update update, String Text);
}
