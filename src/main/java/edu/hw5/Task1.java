package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task1 {

    private static final String SEPARATOR = " - ";

    private Task1() {

    }

    public static List<Duration> timePerSession(List<String> sessions) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        List<Duration> timesPerSession = new ArrayList<>();
        for (String session : sessions) {
            LocalDateTime startTime = LocalDateTime.parse(session.split(SEPARATOR)[0], format);
            LocalDateTime endTime = LocalDateTime.parse(session.split(SEPARATOR)[1], format);
            timesPerSession.add(Duration.between(startTime, endTime));
        }
        return timesPerSession;
    }
}
