package com.thoughtmechanix.licenses.infrastructure.requestinterceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.thoughtmechanix.licenses.infrastructure.httpfilter.usercontext.UserContext;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		String correlationId = UserContext.getCorrelationId();
		log.debug("Entering UserContextInterceptor: " + correlationId);
		
		HttpHeaders headers = request.getHeaders();
		headers.add(UserContext.CORRELATION_ID, correlationId); 
		headers.add(UserContext.AUTH_TOKEN, UserContext.getAuthToken());
		
		log.debug("Leaving UserContextInterceptor");
		return execution.execute(request, body);
	}

}
