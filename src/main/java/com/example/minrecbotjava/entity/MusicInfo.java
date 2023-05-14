package com.example.minrecbotjava.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MusicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "chat_id")
    long chatId;

    @Column(name = "state_id")
    int stateId;
    String fio;
    String phone;
    String email;
    String description;
    @Column(name = "is_active")
    boolean isActive;

    @Override
    public String toString() {
        return "\uD83C\uDD94 Номер заявки: " + id +"\n"+
                "\uD83D\uDC68\u200D\uD83D\uDCBB ФИО: " + fio + '\n' +
                "\uD83D\uDCDE Номер телефона: " + phone + '\n' +
                "\uD83D\uDCE7 email: " + email + '\n' +
                "\uD83E\uDDFE Описание услуги: " + description + '\n';
    }
}
