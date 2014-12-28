package io.perculate.pot;

import static org.junit.Assert.*;
import io.perculate.PerculateWebappApplication;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PerculateWebappApplication.class)
@WebAppConfiguration
public class ReadingRepositoryTest {

	@Inject
	private ReadingRepository readingRepository;
	
	@Test
	public void onSavePopulateCreationDate() {
		Reading entity = new Reading();
		entity.setDeviceId(UUID.randomUUID().toString());
		entity.setWeight(42.0D);
		
		Reading save = readingRepository.save(entity);

		assertNotNull(save.getCreationDate());
	}

	@Test
	public void onSavePopulateId() {
		Reading entity = new Reading();
		String deviceId = UUID.randomUUID().toString();
		entity.setDeviceId(deviceId);
		entity.setWeight(42.0D);
		
		Reading save = readingRepository.save(entity);

		assertNotNull(save.getId());
		assertEquals(deviceId, save.getDeviceId());
	}
}
