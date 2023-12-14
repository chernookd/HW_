package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {
    @Test
    public void threeCharactersAndThirdZeroTrueTest() {
        assertTrue(Task7.threeCharactersAndThirdZero("110111"));
        assertTrue(Task7.threeCharactersAndThirdZero("110"));
        assertTrue(Task7.threeCharactersAndThirdZero("000"));
    }

    @Test
    public void threeCharactersAndThirdZeroFalseTest() {
        assertFalse(Task7.threeCharactersAndThirdZero("130134"));
        assertFalse(Task7.threeCharactersAndThirdZero("21312343"));
        assertFalse(Task7.threeCharactersAndThirdZero("1110"));
    }

    @Test
    public void startsAndEndsTheSameCharTrueTest() {
        assertTrue(Task7.startsAndEndsTheSameChar("1101111"));
        assertTrue(Task7.startsAndEndsTheSameChar("1101"));
        assertTrue(Task7.startsAndEndsTheSameChar("000"));
    }

    @Test
    public void startsAndEndsTheSameCharFalseTest() {
        assertFalse(Task7.startsAndEndsTheSameChar("130131"));
        assertFalse(Task7.startsAndEndsTheSameChar("00000000000020000"));
        assertFalse(Task7.startsAndEndsTheSameChar("010001"));
    }
    @Test
    public void lengthNoLessThan1AndNoMoreThan3TrueTest() {
        assertTrue(Task7.lengthNoLessThan1AndNoMoreThan3("1"));
        assertTrue(Task7.lengthNoLessThan1AndNoMoreThan3("11"));
        assertTrue(Task7.lengthNoLessThan1AndNoMoreThan3("000"));
    }

    @Test
    public void lengthNoLessThan1AndNoMoreThan3FalseTest() {
        assertFalse(Task7.lengthNoLessThan1AndNoMoreThan3("130131"));
        assertFalse(Task7.lengthNoLessThan1AndNoMoreThan3("00000000000010000"));
        assertFalse(Task7.lengthNoLessThan1AndNoMoreThan3(""));
    }

}
