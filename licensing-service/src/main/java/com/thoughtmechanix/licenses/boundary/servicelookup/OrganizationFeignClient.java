package com.thoughtmechanix.licenses.boundary.servicelookup;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtmechanix.licenses.boundary.dto.Organization;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

	@RequestMapping(value="/v1/organizations/{organizationId}")
	Organization getOrganizationInfo(@PathVariable("organizationId") String organizationId);
}
