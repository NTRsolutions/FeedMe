package com.os.foodie.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNameValid(String name) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "^[a-zA-Z\\.\\'\\-_\\s]$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isPhoneValid(String phone) {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[0-9]{8,12}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}