package com.gov.iti.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Validator {
    private static volatile Validator instance = null;
    private Validator(){}

    public static Validator getInstance(){
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    instance = new Validator();
                }
            }
        }
        return instance;
    }

    public boolean validateEmail(String email){
        String emailRegex = "(^[A-Za-z0-9._-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$)| [ \\t\\n]*";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Password must contain at least one digit,
    // one lowercase, one uppercase, and at least 8 characters
    public boolean validatePassword(String password){
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Name must contain only letters and spaces
    public boolean validateName(String name){
        String nameRegex = "^[a-zA-Z]{4,}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // momken n3ml map l kol country han7otha leha el code bta3ha
    public boolean validatePhoneNumber(String phoneNumber, String countryCode) {
        try {
            Phonenumber.PhoneNumber numberProto = PhoneNumberUtil.getInstance().parse(phoneNumber, countryCode);
            return PhoneNumberUtil.getInstance().isValidNumber(numberProto);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
