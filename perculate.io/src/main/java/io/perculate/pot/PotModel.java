package io.perculate.pot;

import io.perculate.readings.Reading;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Reading.class)
public class PotModel {
	private static final Logger logger = LoggerFactory.getLogger(PotModel.class);
	private final MovingAverage movingAverage;
	private final SimpMessagingTemplate messagingTemplate;

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
		logger.info("Handled reading: {}", reading);
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
	
	public Double getAverageValue() {
		return movingAverage.getAverageValue();
	}
}
