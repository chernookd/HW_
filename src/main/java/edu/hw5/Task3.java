package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Task3 {

    private Task3() {
    }

    private final static List<DateTimeFormatter> PATTERNS = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyy-M-d"),
        DateTimeFormatter.ofPattern("M/d/yyyy"),
        DateTimeFormatter.ofPattern("M/d/yy")
    );

    private final static String YESTERDAY = "yesterday";
    private final static String TOMORROW = "tomorrow";
    private final static String TODAY = "today";

    @SuppressWarnings("ReturnCount")
    public static Optional<LocalDate> parseDate(String string) {
        switch (string) {
            case TODAY:
                return Optional.of(LocalDate.now());
            case TOMORROW:
                return Optional.of(LocalDate.now().plusDays(1));
            case YESTERDAY:
                return Optional.of(LocalDate.now().minusDays(1));
            default:
                if (string.contains("ago")) {
                    String[] parts = string.split("\\s+");
                    int daysAgo = Integer.parseInt(parts[0]);
                    return Optional.of(LocalDate.now().minusDays(daysAgo));
                }
        }

        for (DateTimeFormatter pattern : PATTERNS) {
            try {
                LocalDate date = LocalDate.parse(string, pattern);
                return Optional.of(date);
            } catch (DateTimeParseException e) {
            }
        }
        return Optional.empty();
    }
}
