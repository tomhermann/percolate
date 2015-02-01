package io.perculate.readings;

import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ReadingEventHandlerTest {
	@Mock
	private SimpMessagingTemplate messagingTemplate;
	
	@InjectMocks
	private ReadingEventHandler handler;
	
	@Test
	public void whenReadingIsCreatedSendToReadingTopic() throws Exception {
		Reading reading = new Reading();

		handler.handleAfterCreate(reading);
	
		verify(messagingTemplate).convertAndSend("/topic/readings", reading);
	}
}
