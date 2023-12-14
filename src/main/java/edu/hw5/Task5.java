package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {

    private Task5() {

    }

    private final static Pattern PATTERN = Pattern.compile("[QWERTYUIOPASDFGHJKLZXCVBNM]\\d{3}"
        + "[QWERTYUIOPASDFGHJKLZXCVBNM]{2}\\d{2,3}");

    public static boolean checkCarNumber(String number) {
        Matcher matcher = PATTERN.matcher(number);
        return matcher.find();
    }
}
