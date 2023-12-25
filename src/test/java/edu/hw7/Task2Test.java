package edu.hw7;

import edu.hw7.Task2.MultithreadedFactorial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {

    @Test
    public void task2InvalidArgsTest() {
        assertThrows(IllegalArgumentException.class, () -> MultithreadedFactorial.factorial(-1));
    }

    @ParameterizedTest
    @MethodSource("source")
    public void task2CorrectArgsTest(int num, int ans) {
        assertThat(MultithreadedFactorial.factorial(num)).isEqualTo(ans);
    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(0, 1),
            Arguments.of(1, 1),
            Arguments.of(2,2),
            Arguments.of(3, 6),
            Arguments.of(4, 24),
            Arguments.of(5, 120),
            Arguments.of(6, 720));
    }
}
