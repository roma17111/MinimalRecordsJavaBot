package com.example.minrecbotjava.service;

public interface ValidatorService {
    boolean isValidEmail(String email);

    boolean isValidPhoneNumber(String number);

    boolean isValidName(String name);


    boolean isValidFio(String name);

    boolean isValidAge(String age);

    boolean isValidDate(String date);

    boolean isValidSerialPassport(String serial);

    boolean isValidNumberOfPassport(String number);

    boolean isValidCodeOrg(String codeOrg);
}
