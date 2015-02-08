package io.perculate.pot;

import io.perculate.readings.Reading;

import java.util.Date;

public class ReadingWithAverage {
    private final Reading reading;
    private final Double averageValue;

    public ReadingWithAverage(Reading reading, Double averageValue) {
        this.reading = reading;
        this.averageValue = averageValue;
    }

    public Date getCreationDate() {
        return reading.getCreationDate();
    }
    
    public Double getWeight() {
        return reading.getWeight();
    }
    
    public Double getAvgWeight() {
        return averageValue;
    }
}
