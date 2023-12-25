package edu.hw8;

import edu.hw8.Task2.FixedThreadPool;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    private static Stream<Map.Entry<Integer, Integer>> sourse() {
        return Stream.of(
            new AbstractMap.SimpleEntry<>(0,0),
            new AbstractMap.SimpleEntry<>(1,1),
            new AbstractMap.SimpleEntry<>(3,2),
            new AbstractMap.SimpleEntry<>(4,3),
            new AbstractMap.SimpleEntry<>(10,55),
            new AbstractMap.SimpleEntry<>(20,6765)
        );
    }


    @ParameterizedTest
    @MethodSource("sourse")
    public void FixedThreadPoolTest(Map.Entry<Integer, Integer> entry) throws Exception {
        int n = entry.getKey();
        int correctAnswer = entry.getValue();
        FixedThreadPool threadPool = FixedThreadPool.create(Runtime.getRuntime().availableProcessors());
        AtomicReference<AtomicInteger> answer = new AtomicReference<>(new AtomicInteger(0));

        for (int i = 0; i <= n; ++i) {
            int finalI = i;
            threadPool.execute(() -> {
                answer.set(fibonacci(finalI));
            });
        }
        threadPool.start();
        Thread.sleep(10);
        threadPool.stop();
        assertThat(answer.get().get()).isEqualTo(correctAnswer);
    }

    private static AtomicInteger fibonacci(int n) {
        int past = 0;
        int current = 1;
        if (n <= 1) {
            return new AtomicInteger(n);
        }
        for (int i = 2; i <= n; i++) {
            int sum = past + current;
            past = current;
            current = sum;

        }
        return new AtomicInteger(current);
    }
}
