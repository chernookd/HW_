package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task8 {

    private Task8() {

    }

    public static boolean isOddLength(String str) {
        Pattern pattern = Pattern.compile("^([01]{2})*[01]$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLength(String str) {
        Pattern pattern = Pattern.compile("^(?:0[01]{2})*0$|^(?:1[01]{2})*1[01]$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean except11Or111(String str) {
        Pattern pattern = Pattern.compile("^(?!11$)(?!111$)[01]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean containsAtLeastTwo0AndAtMostOne1(String str) {
        Pattern pattern = Pattern.compile("^(1?0{2,}|01?0+|0{2,}1?0*)$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean noConsecutive1(String str) {
        Pattern pattern = Pattern.compile("^(?!.*11)[01]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
