package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.service.ValidatorService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorServiceImpl implements ValidatorService {



    @Override
    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @Override
    public boolean isValidPhoneNumber(String number) {
        String regex = "[0-9*#+() -]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    public boolean isValidName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidAge(String age) {
        if (age.length() > 3) {
            return false;
        }
        for (int i = 0; i < age.length(); i++) {
            if (!Character.isDigit(age.charAt(i))) {
                return false;
            }
        }
        int resultAge = Integer.parseInt(age);
        return resultAge <= 130&&resultAge>6;
    }

    @Override
    public boolean isValidDate(String date) {
        String regex = "^([0-2][0-9]||3[0-1])([.])(0[0-9]||1[0-2])([.])([0-9][0-9])?[0-9][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    @Override
    public boolean isValidSerialPassport(String serial) {
        if (serial.length() > 5) {
            return false;
        }
        if (serial.length() < 4) {
            return false;
        }
        for (int i = 0; i < serial.length(); i++) {
            if (Character.isAlphabetic(serial.charAt(i)) || Character.isLetter(serial.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidNumberOfPassport(String number) {
        if (number.length() != 6) {
            return false;
        }
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidCodeOrg(String codeOrg) {
        if (codeOrg.length() > 8) {
            return false;
        }
        if (codeOrg.length() < 6) {
            return false;
        }
        for (int i = 0; i < codeOrg.length(); i++) {
            if (Character.isAlphabetic(codeOrg.charAt(i))) {
                return false;
            }
            if (codeOrg.charAt(i) == '-' && i != 3) {
                return false;
            }
        }
        return true;
    }

}
