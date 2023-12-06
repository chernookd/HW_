package edu.hw9.Task1;

import java.util.Arrays;

public record Metric(
    String metricName,
    double[] data,
    double sum,
    double average,
    double min,
    double max) {
    @Override
    public String toString() {
        return "metricName - " + metricName + " "
            + "data array - " + Arrays.toString(data) + " " + "sum - " + sum + " "
            + "average - " + average + " " + "min - " + min + " "
            + "max - " + max + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Metric metric = (Metric) obj;

        return metric.average == this.average && Arrays.equals(metric.data, this.data)
            && metric.metricName.equals(this.metricName)
            && metric.max == this.max && metric.sum == this.sum && metric.min == this.min;
    }

    @Override
    public int hashCode() {
        int total = 31;

        total = total * 31 + metricName.hashCode();
        long lSum = Double.doubleToLongBits(sum);
        total = total * 31 + (int) (lSum ^ (lSum >>> 32));
        long lAverage = Double.doubleToLongBits(average);
        total = total * 31 + (int) (lAverage ^ (lAverage >>> 32));
        long lMin = Double.doubleToLongBits(min);
        total = total * 31 + (int) (lMin ^ (lMin >>> 32));
        long lMax = Double.doubleToLongBits(max);
        total = total * 31 + (int) (lMax ^ (lMax >>> 32));
        total = (total * 31) + Arrays.hashCode(data);

        return total;
    }
}
