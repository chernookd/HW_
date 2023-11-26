package edu.hw7.Task4;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("HideUtilityClassConstructor")
public class OneThreadCountsPi {

    private static long totalCount;

    private static long circleCount = 0;

    private static final int SIDE = 100000;
    private static final int RADIUS = (SIDE / 2);

    @SuppressWarnings("MagicNumber")
    public static double getPi(long total) {

        totalCount = total;
        circleCount = 0;

        for (int i = 0; i < totalCount; i++) {
            long x = ThreadLocalRandom.current().nextInt(0, SIDE + 1);
            long y = ThreadLocalRandom.current().nextInt(0, SIDE + 1);

            if (Math.sqrt(Math.pow(RADIUS - x, 2) + Math.pow(RADIUS - y, 2)) <= RADIUS) {
                circleCount++;
            }
        }

        return 4.0 * ((double) circleCount / totalCount);
    }
}
