package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {

    @Test
    public void checkValidPasswordTest() {
        assertTrue(Task4.checkPassword("13!"));
        assertTrue(Task4.checkPassword("13~"));
        assertTrue(Task4.checkPassword("13@"));
        assertTrue(Task4.checkPassword("13#"));
        assertTrue(Task4.checkPassword("13&"));
        assertTrue(Task4.checkPassword("13$"));
        assertTrue(Task4.checkPassword("13%"));
        assertTrue(Task4.checkPassword("13^"));
        assertTrue(Task4.checkPassword("13*"));
    }

    @Test
    public void checkInvalidPasswordTest(){
        assertFalse(Task4.checkPassword("13"));
        assertFalse(Task4.checkPassword(""));
        assertFalse(Task4.checkPassword(" "));
        assertFalse(Task4.checkPassword("123456789qwertyuiop[asdfghjkl;zxcvbnm,."));
    }

}
