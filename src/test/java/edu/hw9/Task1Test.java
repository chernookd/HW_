package edu.hw9;

import edu.hw9.Task1.Metric;
import edu.hw9.Task1.StatsCollector;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {

    List<Metric> correctMetrics = new ArrayList<>(List
        .of(new Metric("metric0", new double[]{0.0, 1.0, 2.0}, 3, 1, 0, 2),
        new Metric("metric1", new double[]{1.0, 2.0, 3.0}, 6, 2, 1, 3),
        new Metric("metric2", new double[]{2.0, 3.0, 4.0}, 9, 3, 2, 4),
        new Metric("metric3", new double[]{3.0, 4.0, 5.0}, 12, 4, 3, 5),
        new Metric("metric4", new double[]{4.0, 5.0, 6.0}, 15, 5, 4, 6),
        new Metric("metric5", new double[]{5.0, 6.0, 7.0}, 18, 6, 5, 7),
        new Metric("metric6", new double[]{6.0, 7.0, 8.0}, 21, 7, 6, 8),
        new Metric("metric7", new double[]{7.0, 8.0, 9.0}, 24, 8, 7, 9),
        new Metric("metric8", new double[]{8.0, 9.0, 10.0}, 27, 9, 8, 10),
        new Metric("metric9", new double[]{9.0, 10.0, 11.0}, 30, 10, 9, 11),
        new Metric("metric10", new double[]{10.0, 11.0, 12.0}, 33, 11, 10, 12),
        new Metric("metric11", new double[]{11.0, 12.0, 13.0}, 36, 12, 11, 13)));

    @Test
    public void StatsCollectorTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        StatsCollector statsCollector = new StatsCollector();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            int finalI = i;
            executorService.execute(() -> {
                statsCollector.push("metric" + finalI, new double[]{finalI, finalI + 1, finalI + 2});
            });
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Metric correctMetric = correctMetrics.get(i);
            Metric metric = statsCollector.getStatsByMetricName(correctMetrics.get(i).metricName()).getFirst();
            assertThat(correctMetric.equals(metric)).isEqualTo(true);
        }
    }

    @Test
    public void InvalidArgsStatsCollectorTest() throws InterruptedException {
        StatsCollector statsCollector = new StatsCollector();
        statsCollector.push(null, null);

        assertThat(statsCollector.getAllStats()).isEqualTo(new ArrayList<>());

        statsCollector.push("null", null);
        assertThat(statsCollector.getAllStats()).isEqualTo(new ArrayList<>());

        statsCollector.push(null, new double[]{1.0});
        assertThat(statsCollector.getAllStats()).isEqualTo(new ArrayList<>());
    }
}
