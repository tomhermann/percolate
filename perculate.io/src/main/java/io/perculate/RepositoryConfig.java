package io.perculate;

import io.perculate.readings.ReadingEventHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
public class RepositoryConfig extends RepositoryRestMvcConfiguration {

	@Bean
	public ReadingEventHandler readingEventHandler() {
		return new ReadingEventHandler();
	}
}
