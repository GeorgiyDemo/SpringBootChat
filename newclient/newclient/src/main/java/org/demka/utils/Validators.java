package org.demka.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    /**
     * Проверка e-mail на корректность
     * @param email - исходный e-mail
     * @return - результат валидации
     */
    public static boolean emailValidator(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
