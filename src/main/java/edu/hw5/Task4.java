package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {

    private Task4() {

    }

    private final static Pattern PATTERN = Pattern.compile("[~!@#$%^&*|]");

    public static boolean checkPassword(String password) {
        Matcher matcher = PATTERN.matcher(password);
        return matcher.find();
     }
}
