package com.thoughtmechanix.zuul.filter.pre;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.thoughtmechanix.zuul.filter.FilterUtils;

@Component
public class TrackingFilter extends ZuulFilter {
	
	private static final Logger LOG = LoggerFactory.getLogger(TrackingFilter.class);
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	
	@Autowired
	private FilterUtils filterUtils;

	@Override
	public Object run() {
		String correlationId = filterUtils.getCorrelationId();
		
		if (StringUtils.isNotEmpty(correlationId)) {
			LOG.debug(String.format("tmx-correlation-id found in tracking filter: %s. ",
					filterUtils.getCorrelationId()));
		} else {
			String generated = generateCorrelationId();
			filterUtils.setCorrelationId(generated);
			LOG.debug(String.format("tmx-correlation-id generated in tracking filter: %s. ",
					generated));
		}
		
		return null;
	}

	private String generateCorrelationId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public String filterType() {		
		return FilterUtils.PRE_FILTER_TYPE;
	}

}
