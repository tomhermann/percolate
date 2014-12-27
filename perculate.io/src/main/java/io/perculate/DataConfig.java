package io.perculate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class DataConfig {

	@Bean
	public AuditorAware<String> auditor() {
		return new AuditorAware<String>() {
			@Override
			public String getCurrentAuditor() {
				return "perculate.io";
			}
		};
	}
}
