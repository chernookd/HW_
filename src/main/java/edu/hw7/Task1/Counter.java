package edu.hw7.Task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    static public AtomicInteger counter = new AtomicInteger(0);

    public int multiThreadedIncrease(int counterStart, int numOfInc) throws IllegalArgumentException {

        if (numOfInc < 1) {
            throw new IllegalArgumentException();
        }

        IncThread[] threads = new IncThread[numOfInc];
        counter = new AtomicInteger(counterStart);

        for (int i = 0; i < numOfInc; i++) {
            threads[i] = new IncThread();
        }

        for (int i = 0; i < numOfInc; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numOfInc; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return counter.get();
    }
}
