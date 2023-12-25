package edu.hw7;

import edu.hw7.Task4.MultithreadedPi;
import edu.hw7.Task4.OneThreadCountsPi;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class Task4Test {
    private static final long COUNT_SIMULATION1 = 10_000_000;
    private static final long COUNT_SIMULATION2 = 100_000_000;
    private static final long COUNT_SIMULATION3 = 1_000_000_000;

    @Test
    @Disabled
    void Task4WriteErrorLevelTest() throws InterruptedException {
        double delta1 = Math.abs(Math.PI - OneThreadCountsPi.getPi(COUNT_SIMULATION1));
        double delta2 = Math.abs(Math.PI - OneThreadCountsPi.getPi(COUNT_SIMULATION2));
        double delta3 = Math.abs(Math.PI - OneThreadCountsPi.getPi(COUNT_SIMULATION3));

        System.out.println("delta1 of COUNT_SIMULATION1  " + delta1);
        System.out.println("delta2 of COUNT_SIMULATION2  " + delta2);
        System.out.println("delta3 of COUNT_SIMULATION3  " + delta3);

    }


    @Test
    @Disabled
    void Task4PerformanceAccelerationTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        new OneThreadCountsPi().getPi(COUNT_SIMULATION1);
        long end = System.currentTimeMillis();
        long timeOfOneThread = end - start;

        long start1 = System.currentTimeMillis();
        MultithreadedPi.getPi(COUNT_SIMULATION1, 2);
        long end1 = System.currentTimeMillis();
        long timeOfTwoThreads = end1 - start1;

        long start2 = System.currentTimeMillis();
        MultithreadedPi.getPi(COUNT_SIMULATION1, 6);
        long end2 = System.currentTimeMillis();
        long timeOfSixThreads = end2 - start2;

        long start3 = System.currentTimeMillis();
        MultithreadedPi.getPi(COUNT_SIMULATION1, 10);
        long end3 = System.currentTimeMillis();
        long timeOfTenThreads = end3 - start3;


        long start4 = System.currentTimeMillis();
        MultithreadedPi.getPi(COUNT_SIMULATION1, 100);
        long end4 = System.currentTimeMillis();
        long timeOfOneHundredThreads = end4 - start4;

        double averageAccelerationTime = ((double) ((-timeOfTwoThreads + timeOfOneThread) + (-timeOfSixThreads + timeOfOneThread)
                + (-timeOfTenThreads + timeOfOneThread) + (-timeOfOneHundredThreads + timeOfOneThread)) / 4);


        System.out.println("\n1 thread - " + timeOfOneThread + "ms");
        System.out.println("2 thread - " + timeOfTwoThreads + "ms");
        System.out.println("6 thread - " + timeOfSixThreads + "ms");
        System.out.println("10 thread - " + timeOfTenThreads + "ms");
        System.out.println("100 thread - " + timeOfOneHundredThreads + "ms");

        assertThat(timeOfOneHundredThreads).isLessThan(timeOfOneThread);
        assertThat(timeOfTenThreads).isLessThan(timeOfOneThread);
        assertThat(timeOfSixThreads).isLessThan(timeOfOneThread);
        assertThat(timeOfTwoThreads).isLessThan(timeOfOneThread);

        System.out.println("Average acceleration time " + averageAccelerationTime + "ms");

    }
}
