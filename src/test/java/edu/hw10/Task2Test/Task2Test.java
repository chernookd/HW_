package edu.hw10.Task2Test;

import edu.hw10.Task2.CacheProxy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    File cacheFile = new File("CacheDir/cache.ser");
    static File cacheDir = new File("CacheDir");

    @MethodSource
    public static Stream<Map.Entry<Integer, Integer>> sourseSum() {
        return Stream.of(
            Map.entry(1, 1),
            Map.entry(1, 1),
            Map.entry(2, 3),
            Map.entry(2, 3),
            Map.entry(2, 3),
            Map.entry(1, 1),
            Map.entry(3, 6),
            Map.entry(4, 10),
            Map.entry(5, 15),
            Map.entry(6, 21),
            Map.entry(7, 28)
            );
    }

    @ParameterizedTest
    @MethodSource("sourseSum")
    void task2CacheProxySumTest(Map.Entry<Integer, Integer> entry) {
        try {
            Sum sum = new SumNumbers();
            Sum proxy = CacheProxy.create(sum, Sum.class,
                null);
            int num = entry.getKey();
            int correctAns = entry.getValue();

            assertThat(proxy.sum(num)).isEqualTo(correctAns);
        } catch (Exception e) {;
        }
    }

    @Test
    void task2FileCacheTest () {
        try {
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
            }
            FibCalculator fibCalculator = new FibCalculatorImpl();
            FibCalculator proxy = CacheProxy.create(fibCalculator, FibCalculator.class,
                cacheFile);

            assertThat(cacheFile.length()).isEqualTo(0);

            assertThat(proxy.fib(1)).isEqualTo(1);
            long lengthAfterFirstCache = cacheFile.length();
            assertThat(proxy.fib(1)).isEqualTo(1);
            assertThat(proxy.fib(1)).isEqualTo(1);
            long lengthAfterSeveralIdenticalCaches = cacheFile.length();
            assertThat(cacheFile.length() > 0).isEqualTo(true);
            assertThat(lengthAfterFirstCache == lengthAfterSeveralIdenticalCaches).isEqualTo(true);
            assertThat(proxy.fib(10)).isEqualTo(55);
            assertThat(proxy.fib(10)).isEqualTo(55);
            long lengthAfterSeveralDifferentCaches = cacheFile.length();

            assertThat(lengthAfterSeveralDifferentCaches > lengthAfterSeveralIdenticalCaches)
                .isEqualTo(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void deleteCacheFiles() {
        for (File file: cacheDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }
}
