package edu.hw10.Task2Test;

import edu.hw10.Task2.Cache;
import java.util.stream.IntStream;

public class SumNumbers implements Sum{
    @Override
    @Cache(persist = true)
    public int sum(int num) {
        return IntStream.rangeClosed(0, num).sum();
    }
}
