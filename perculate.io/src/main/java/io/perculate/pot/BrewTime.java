package io.perculate.pot;

import io.perculate.readings.Reading;

public class BrewTime {
    private long timestamp;
    private Reading reading;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public Reading getReading() {
        return reading;
    }
}
