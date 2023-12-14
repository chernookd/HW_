package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {
    @Test
    public void checkValidContainSubstringTest() {
        assertTrue(Task6.doesContainSubstring("abc", "achfdaabgacaabg"));
        assertTrue(Task6.doesContainSubstring("abc", "dddddabcdddddd"));
        assertTrue(Task6.doesContainSubstring("", "dddddabcdddddd"));
    }

    @Test
    public void checkInvalidCarNumberTest(){
        assertFalse(Task6.doesContainSubstring("abc", "achfdaabgaaabg"));
        assertFalse(Task6.doesContainSubstring("abc", ""));
        assertFalse(Task6.doesContainSubstring(" ", "fffr4rrrg4g45"));
    }
}
