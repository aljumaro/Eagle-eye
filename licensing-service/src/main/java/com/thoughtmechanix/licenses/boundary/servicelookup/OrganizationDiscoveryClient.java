package com.thoughtmechanix.licenses.boundary.servicelookup;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.licenses.boundary.dto.Organization;

@Component
public class OrganizationDiscoveryClient {
	
	private static final String organizationServiceUriTemplate = "%sv1/organizations/%s";

	private DiscoveryClient discoveryClient;

	@Autowired
	public void setDiscoveryClient(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}
	
	public Organization getOrganizationInfo(String organizationId) {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
		
		if (CollectionUtils.isEmpty(instances)) {
			return null;
		}
		
		String organizationUrl = String.format(organizationServiceUriTemplate, instances.get(0).getUri().toString(), organizationId);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Organization> exchanged = restTemplate.exchange(organizationUrl, HttpMethod.GET, null, Organization.class, organizationId);
		
		return exchanged.getBody();
		
	}
}
