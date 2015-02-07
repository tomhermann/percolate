package io.perculate.readings;

import io.perculate.PerculateWebappApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

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

	@Test
	public void findLastReading() {
		Reading entity = new Reading();
		entity.setWeight(42.0D);
		readingRepository.save(entity);

        Reading result = readingRepository.findTopByOrderByCreationDateDesc();

        assertNotNull(result);
	}
}
