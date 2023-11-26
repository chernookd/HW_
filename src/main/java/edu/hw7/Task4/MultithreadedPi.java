package edu.hw7.Task4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("HideUtilityClassConstructor")
public class MultithreadedPi {

    private final static int NUM_OF_CORES = 6;
    private static AtomicLong circleCount;

    private static final int SIDE = 1000000;
    private static final int RADIUS = (SIDE / 2);

    private static long getCircleCount(long total) {
        long count = 0;

        for (int i = 0; i < total; i++) {
            long x = ThreadLocalRandom.current().nextInt(0, SIDE + 1);
            long y = ThreadLocalRandom.current().nextInt(0, SIDE + 1);

            if (Math.sqrt(Math.pow(RADIUS - x, 2) + Math.pow(RADIUS - y, 2)) <= RADIUS) {
                count++;
            }
        }
        return count;
    }

    @SuppressWarnings("MagicNumber")
    static public double getPi(long total, int numOfThread) throws InterruptedException {
        circleCount = new AtomicLong(0);
        int countOfThreads = numOfThread;
        if (countOfThreads < 1) {
            countOfThreads = NUM_OF_CORES;
        }

        int finalCountOfThreads = countOfThreads;
        Thread[] threads = new Thread[countOfThreads];

        for (int i = 0; i < countOfThreads; i++) {
            threads[i] = new Thread(() -> {
                circleCount.addAndGet(getCircleCount(total / finalCountOfThreads));
            });
            threads[i].start();
        }

        for (int i = 0; i < countOfThreads; i++) {
            threads[i].join();
        }

        return 4 * ((double) circleCount.get() / total);
    }
}
