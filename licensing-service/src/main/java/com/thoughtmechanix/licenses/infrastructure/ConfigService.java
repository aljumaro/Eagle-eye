package com.thoughtmechanix.licenses.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
	
	@Value("${tracer.property}")
	private String tracerProperty;

	public String getTracerProperty() {
		return tracerProperty;
	}

}
