package com.thoughtmechanix.zuul.filter.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thoughtmechanix.zuul.filter.FilterUtils;

@Component
public class ResponseFilter extends ZuulFilter {

	private static final Logger log = LoggerFactory.getLogger(ResponseFilter.class);

	@Autowired
	private FilterUtils filterUtils;

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		log.debug("Adding the correlation id to the outbound headers: " + filterUtils.getCorrelationId());
		ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
		log.debug(String.format("Completing outgoing request for %s.", ctx.getRequest().getRequestURI()));
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return FilterUtils.POST_FILTER_TYPE;
	}

}
