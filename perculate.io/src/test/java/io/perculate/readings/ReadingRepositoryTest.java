package io.perculate.readings;

import static org.junit.Assert.assertNotNull;
import io.perculate.PerculateWebappApplication;
import io.perculate.readings.Reading;
import io.perculate.readings.ReadingRepository;

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
		entity.setWeight(42.0D);
		
		Reading save = readingRepository.save(entity);

		assertNotNull(save.getCreationDate());
	}
}
