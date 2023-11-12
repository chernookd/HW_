package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {

    private Task6() {

    }

    public static boolean doesContainSubstring(String subString, String mainString) {
        Pattern pattern = Pattern.compile(createPattern(subString));
        Matcher matcher = pattern.matcher(mainString);
        return matcher.find();
    }

    public static String createPattern(String str) {
        StringBuilder patternString = new StringBuilder();
        for (int i = 0; i < str.toCharArray().length; i++) {
            patternString.append(str.toCharArray()[i]).append(".*");
        }
        return patternString.toString();
    }

}
