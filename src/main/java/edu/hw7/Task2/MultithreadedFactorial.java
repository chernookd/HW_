package edu.hw7.Task2;

import java.util.List;
import java.util.stream.IntStream;

public class MultithreadedFactorial {

    private MultithreadedFactorial() {

    }

    public static int factorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        List<Integer> list = IntStream.range(1, num + 1).boxed().toList();

        return list.parallelStream().reduce((integer, integer2) -> integer * integer2).orElse(Integer.valueOf(1));
    }
}
