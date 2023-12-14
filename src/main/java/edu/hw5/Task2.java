package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {

    private static final int INDEX_OF_13TH_FRIDAY = 13;

    private static final int QUANTITY_OF_MONTH = 12;



    private Task2() {

    }

    public static List<LocalDate> allFridays13thByYear(int year) {
        List<LocalDate> listFridays13th = new ArrayList<>();
        for (int i = 1; i <= QUANTITY_OF_MONTH; i++) {
            if (LocalDate.of(year, i, INDEX_OF_13TH_FRIDAY).getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                listFridays13th.add(LocalDate.of(year, i, INDEX_OF_13TH_FRIDAY));
            }
        }
        return listFridays13th;
    }

    public static LocalDate nextFriday13th(LocalDate date) {
        LocalDate currentDate = date;
        TemporalAdjuster nextFriday = TemporalAdjusters.next(DayOfWeek.FRIDAY);
        currentDate = currentDate.with(nextFriday);
        while (!(currentDate.getDayOfMonth() == INDEX_OF_13TH_FRIDAY)) {
            currentDate = currentDate.with(nextFriday);
        }
        return currentDate;
    }
}
