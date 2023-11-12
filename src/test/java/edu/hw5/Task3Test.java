package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task3Test {

    private static Stream<Arguments> getValidFormatDate(){
        return Stream.of(
            Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234))),
            Arguments.of("1/7/13", Optional.of(LocalDate.of(2013, 1, 7))),
            Arguments.of("2012-11-11", Optional.of(LocalDate.of(2012, 11, 11))),
            Arguments.of("1/2/2006", Optional.of(LocalDate.of(2006, 1, 2))),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today", Optional.of(LocalDate.now()))
        );
    }

    @ParameterizedTest
    @MethodSource("getValidFormatDate")
    void correctFormatTest(String date, Optional<LocalDate> correctRes){
        assertThat(Task3.parseDate(date)).isEqualTo(correctRes);
    }


    private static Stream<Arguments> getInvalidFormatDate(){
        return Stream.of(
            Arguments.of("TOMORROW"),
            Arguments.of("TODAY"),
            Arguments.of("rergerg"),
            Arguments.of("1 day ag"),
            Arguments.of("2020 1 1"),
            Arguments.of("2020-1/2"),
            Arguments.of("1/31976"),
            Arguments.of("1/3 /20"),
            Arguments.of("2234 Days"),
            Arguments.of("two Days"),
            Arguments.of(""),
            Arguments.of(" ")
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidFormatDate")
    void test_correct_format(String date){
        Optional<LocalDate> resultOfWork = Task3.parseDate(date);

        assertThat(resultOfWork).isEqualTo(Optional.empty());
    }
}
