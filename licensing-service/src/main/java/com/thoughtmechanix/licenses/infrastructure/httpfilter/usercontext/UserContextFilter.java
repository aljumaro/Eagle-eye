package com.thoughtmechanix.licenses.infrastructure.httpfilter.usercontext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserContextFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserContextFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
		
		LOG.debug("Entering UserContextFilter: " + correlationId);
		
		
		UserContext.setCorrelationId(correlationId);
		UserContext.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
		UserContext.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
		UserContext.setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));
		
		LOG.debug("Leaving UserContextFilter");
		
		chain.doFilter(httpServletRequest, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
