package edu.hw9.Task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class StatsCollector {
    private final static Long TIME_OUT_SECONDS = 10L;
    private final List<Metric> metrics = new CopyOnWriteArrayList<>();
    Lock lock = new ReentrantLock();
    private final ExecutorService executorService
        = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void push(String metricName, double[] data) {
        if (metricName == null || data == null || data.length == 0) {
            return;
        }
        executorService.execute(() -> {
                double sum = Arrays.stream(data).sum();
                double average = sum / data.length; //Arrays.stream(numbers).average().orElse(0);
                OptionalDouble optionalMin = Arrays.stream(data).min();
                OptionalDouble optionalMax = Arrays.stream(data).max();
                double min;
                double max;
                if (optionalMin.isPresent()) {
                    min = Double.MIN_VALUE;
                }
                if (optionalMax.isPresent()) {
                    max = Double.MAX_VALUE;
                }
                min = optionalMin.getAsDouble();
                max = optionalMax.getAsDouble();

                Metric metric = new Metric(metricName, data, sum, average, min, max);

                lock.lock();
                metrics.add(metric);
                lock.unlock();
            }
        );
    }

    public List<Metric> getAllStats() throws InterruptedException {
        executorService.shutdown();
        if (executorService.awaitTermination(TIME_OUT_SECONDS, TimeUnit.SECONDS)) {
            return new ArrayList<>(metrics);
        } else {
            throw new InterruptedException();
        }
    }

    public List<Metric> getStatsByMetricName(String metricName) throws InterruptedException {
        executorService.shutdown();
        if (executorService.awaitTermination(TIME_OUT_SECONDS, TimeUnit.SECONDS)) {
            return metrics.stream()
                .filter(metric -> metric.metricName().equals(metricName))
                .collect(Collectors.toList());
        } else {
            throw new InterruptedException();
        }
    }
}
