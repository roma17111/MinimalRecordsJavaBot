package com.example.minrecbotjava.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "chat_id")
    long chatId;

    @Column(name = "nick_name")
    String nickName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "is_admin")
    boolean isAdmin = false;


    @Override
    public String toString() {
        return "\uD83C\uDD94 " + id + '\n' +
                "<b>chat</b>\uD83C\uDD94 " + chatId +'\n' +
                "\uD83C\uDF10 Ник: @" + nickName + '\n' +
                "\uD83E\uDD35 Имя: " + firstName + '\n' +
                "\uD83E\uDDCD\uD83C\uDFFD Фамилия: " + lastName + '\n' +
                ("\uD83C\uDFAD Роль: " + (isAdmin ? "Админ" : "Пользователь")) + '\n';

    }
}
