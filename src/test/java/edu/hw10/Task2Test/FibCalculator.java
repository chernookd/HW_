package edu.hw10.Task2Test;

import edu.hw10.Task2.Cache;

public interface FibCalculator {
    @Cache(persist = true)
    long fib(int number);
}
