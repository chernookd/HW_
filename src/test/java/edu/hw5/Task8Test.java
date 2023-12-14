package edu.hw5;

import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {
    @Test
    public void isOddLengthTrueTest() {
        assertTrue(Task8.isOddLength("110011001"));
        assertTrue(Task8.isOddLength("11111"));
        assertTrue(Task8.isOddLength("1"));
    }

    @Test
    public void isOddLengthFalseTest() {

        assertFalse(Task8.isOddLength("110111"));
        assertFalse(Task8.isOddLength("1101"));
        assertFalse(Task8.isOddLength("001322222220"));
    }

    @Test
    public void startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLengthTrueTest() {
        assertTrue(Task8.startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLength("0110"));
        assertTrue(Task8.startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLength("11011"));
    }

    @Test
    public void startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLengthFalseTest() {
        assertFalse(Task8.startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLength("1100"));
        assertFalse(Task8.startsAt0AndHasAnOddLengthOrStartsAt1AndHasAnEvenLength("01010"));
    }
    @Test
    public void except11Or111TrueTest() {
        assertTrue(Task8.except11Or111("00110"));
        assertTrue(Task8.except11Or111("11011"));
        assertTrue(Task8.except11Or111("110"));

    }

    @Test
    public void except11Or111FalseTest() {
        assertFalse(Task8.except11Or111("11"));
        assertFalse(Task8.except11Or111("111"));
        assertFalse(Task8.except11Or111("113"));
        assertFalse(Task8.except11Or111("1221"));
    }
    @Test
    public void containsAtLeastTwo0AndAtMostOne1TrueTest() {
        assertTrue(Task8.containsAtLeastTwo0AndAtMostOne1("0010"));
        assertTrue(Task8.containsAtLeastTwo0AndAtMostOne1("100"));
        assertTrue(Task8.containsAtLeastTwo0AndAtMostOne1("0000000000000001"));

    }

    @Test
    public void containsAtLeastTwo0AndAtMostOne1FalseTest() {
        assertFalse(Task8.containsAtLeastTwo0AndAtMostOne1("0011"));
        assertFalse(Task8.containsAtLeastTwo0AndAtMostOne1("01"));
        assertFalse(Task8.containsAtLeastTwo0AndAtMostOne1("00012"));
        assertFalse(Task8.containsAtLeastTwo0AndAtMostOne1(""));
    }

    @Test
    public void noConsecutive1TrueTest() {
        assertTrue(Task8.noConsecutive1("0010"));
        assertTrue(Task8.noConsecutive1("100"));
        assertTrue(Task8.noConsecutive1("10101010101010101"));

    }

    @Test
    public void noConsecutive1FalseTest() {
        assertFalse(Task8.noConsecutive1("0011"));
        assertFalse(Task8.noConsecutive1("1101"));
        assertFalse(Task8.noConsecutive1("00012"));
        assertFalse(Task8.noConsecutive1(""));
    }

    public static boolean noConsecutive1(String str) {
        Pattern pattern = Pattern.compile("^(?!.*11)[01]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}
