package com.thoughtmechanix.licenses.boundary.servicelookup;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.licenses.boundary.dto.Organization;

@Component
public class OrganizationRestTemplateClient{
	
	private RestTemplate restTemplate;

	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Optional<Organization> getOrganizationInfo(String organizationId) {
		
		ResponseEntity<Organization> restExchange =
				restTemplate.exchange(
				"http://organizationservice/v1/organizations/{organizationId}",
				HttpMethod.GET,
				null, Organization.class, organizationId);
		
		return Optional.of(restExchange.getBody());
	}
	
	

}
