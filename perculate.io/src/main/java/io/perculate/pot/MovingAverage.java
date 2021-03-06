package io.perculate.pot;

import java.util.Collection;

import com.google.common.collect.EvictingQueue;

public class MovingAverage {
    private final EvictingQueue<Double> values;

    public MovingAverage(int maxPoints) {
        this.values = EvictingQueue.create(maxPoints);
    }

    public void addValue(double value) {
        values.add(Double.valueOf(value));
    }

    public Double getAverageValue() {
        if (values.isEmpty()) {
            return Double.NaN;
        }
        return sum(values) / values.size();
    }

    private static double sum(Collection<Double> values) {
        return values.stream().parallel().mapToDouble(Double::doubleValue).sum();
    }

}