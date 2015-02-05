package io.perculate.readings;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Reading.class)
public class ReadingEventHandler {
	private static final Logger logger = LoggerFactory.getLogger(ReadingEventHandler.class);
	private final SimpMessagingTemplate messagingTemplate;

	@Inject
	public ReadingEventHandler(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@HandleAfterCreate
	public void handleAfterCreate(Reading reading) {
		messagingTemplate.convertAndSend("/topic/readings", reading);
		logger.debug("Sent -> '/topic/readings': {}", reading);
	}
}
