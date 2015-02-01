package io.perculate.readings;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RepositoryEventHandler(Reading.class)
public class ReadingEventHandler {
	private static final Logger logger = LoggerFactory.getLogger(ReadingEventHandler.class);

	@Inject
	private SimpMessagingTemplate messagingTemplate;
	
	@HandleAfterCreate
	public void handleAfterCreate(Reading reading) {
		logger.info("Created reading: {}" , reading);
		messagingTemplate.convertAndSend("/topic/readings", reading);
	}
}
