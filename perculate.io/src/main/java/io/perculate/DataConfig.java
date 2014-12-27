package io.perculate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DataConfig {
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareProvider();
	}
}
