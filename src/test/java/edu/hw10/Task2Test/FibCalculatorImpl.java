package edu.hw10.Task2Test;

public class FibCalculatorImpl implements FibCalculator {

    @Override
    public long fib(int num) {
        if (num < 2) {
            return num;
        }

        return fib(num - 1) + fib(num - 2);
    }
}
