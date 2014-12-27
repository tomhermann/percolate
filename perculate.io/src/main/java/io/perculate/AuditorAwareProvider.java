package io.perculate;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareProvider implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		return "perculate.io";
	}
}
