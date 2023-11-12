package edu.hw5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Task1Test {


    @ParameterizedTest
    @MethodSource("sessionProvider")
    void testTimePerSession(List<String> sessions, List<Duration> expectedDurations) {
        List<Duration> actualDurations = Task1.timePerSession(sessions);
        Assertions.assertEquals(expectedDurations, actualDurations);
    }

    private static Stream<Object[]> sessionProvider() {
        return Stream.of(
            new Object[] {
                Arrays.asList(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                Arrays.asList(
                    Duration.ofHours(3).plusMinutes(30),
                    Duration.ofHours(3).plusMinutes(50)
                )
            },
            new Object[] {
                Arrays.asList(
                    "2022-05-10, 08:00 - 2022-05-10, 08:30",
                    "2022-05-10, 09:00 - 2022-05-10, 10:00",
                    "2022-05-10, 10:30 - 2022-05-10, 12:00"
                ),
                Arrays.asList(
                    Duration.ofMinutes(30),
                    Duration.ofHours(1),
                    Duration.ofHours(1).plusMinutes(30))
            }
        );
    }
}
