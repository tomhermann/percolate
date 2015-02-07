package io.perculate.pot;

import io.perculate.readings.Reading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@RepositoryEventHandler(Reading.class)
public class PotModel {
    private static final Logger logger = LoggerFactory.getLogger(PotModel.class);
    private final MovingAverage movingAverage;
    private final SimpMessagingTemplate messagingTemplate;
    private BrewTime brewTime;

    @Inject
    public PotModel(SimpMessagingTemplate messagingTemplate) {
        this.movingAverage = new MovingAverage(25);
        this.messagingTemplate = messagingTemplate;
    }

    @HandleAfterCreate
    public void handleReading(Reading reading) {
        sendReading(reading);
        updateAverage(reading);
        sendAverageReading(reading);
        sendBrewTime();
        logger.info("Handled reading: {}", reading);
    }

    public Double getAverageValue() {
        return movingAverage.getAverageValue();
    }

    public void setBrewTime(BrewTime brewTime) {
        this.brewTime = brewTime;
        sendBrewTime();
    }

    private void sendBrewTime() {
        if(brewTime != null) {
            messagingTemplate.convertAndSend("/topic/brewTime", brewTime);
        }
    }

    public BrewTime getBrewTime() {
        return brewTime;
    }

    private void updateAverage(Reading reading) {
        movingAverage.addValue(reading.getWeight());
    }

    private void sendReading(Reading reading) {
        messagingTemplate.convertAndSend("/topic/readings", reading);
    }

    private void sendAverageReading(Reading reading) {
        AverageReading avgReading = AverageReading.of(reading.getCreationDate(), getAverageValue());
        messagingTemplate.convertAndSend("/topic/averageReadings", avgReading);
    }
}
