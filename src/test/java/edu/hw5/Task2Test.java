package edu.hw5;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {

    @Test
    public void allFridays13thByYearTest() {
        List<LocalDate> testLocaleDate = Task2.allFridays13thByYear(1925);
        List<LocalDate> correctAnswer = new ArrayList<>();
        correctAnswer.add(LocalDate.of(1925, 2, 13));
        correctAnswer.add(LocalDate.of(1925, 3, 13));
        correctAnswer.add(LocalDate.of(1925, 11, 13));

        assertArrayEquals(testLocaleDate.toArray(), correctAnswer.toArray());

        List<LocalDate> testLocaleDate2 = Task2.allFridays13thByYear(2024);
        List<LocalDate> correctAnswer2 = new ArrayList<>();
        correctAnswer2.add(LocalDate.of(2024, 9, 13));
        correctAnswer2.add(LocalDate.of(2024, 12, 13));

        assertArrayEquals(testLocaleDate2.toArray(), correctAnswer2.toArray());


    }

    @Test
    public void nextFridays13thTest() {
        LocalDate firstTest = LocalDate.of(2023, 1, 10);
        LocalDate firstAnswer = LocalDate.of(2023, 1, 13);

        assertEquals(Task2.nextFriday13th(firstTest), firstAnswer);
    }
}
