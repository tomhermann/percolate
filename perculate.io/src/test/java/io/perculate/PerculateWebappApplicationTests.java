package io.perculate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PerculateWebappApplication.class)
@WebAppConfiguration
public class PerculateWebappApplicationTests {

	@Test
	public void contextLoads() {
	}

}
