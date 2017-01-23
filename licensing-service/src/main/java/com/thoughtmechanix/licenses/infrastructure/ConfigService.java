package com.thoughtmechanix.licenses.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class ConfigService {
	
	@Value("${tracer.property}")
	private String tracerProperty;

	public String getTracerProperty() {
		return tracerProperty;
	}

}
