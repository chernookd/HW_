package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Test {

    @Test
    public void checkValidCarNumberTest() {
        assertTrue(Task5.checkCarNumber("A123BE777"));
        assertTrue(Task5.checkCarNumber("O777OO177"));
    }

    @Test
    public void checkInvalidCarNumberTest(){
        assertFalse(Task5.checkCarNumber("123АВЕ777"));
        assertFalse(Task5.checkCarNumber("А123ВГ77"));
        assertFalse(Task5.checkCarNumber(" "));
        assertFalse(Task5.checkCarNumber("А123ВЕ7777"));
    }
}
