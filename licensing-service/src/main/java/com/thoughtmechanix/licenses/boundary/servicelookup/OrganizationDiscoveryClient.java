package com.thoughtmechanix.licenses.boundary.servicelookup;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.licenses.boundary.dto.Organization;

@Component
public class OrganizationDiscoveryClient{

	private static final String ORGANIZATION_SERVICE_APP_NAME = "organizationservice";
	private static final String ORGANIZACION_SERVICE_URI_TEMPLATE = "%sv1/organizations/%s";

	private DiscoveryClient discoveryClient;

	@Autowired
	public void setDiscoveryClient(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	public Optional<Organization> getOrganizationInfo(String organizationId) {

		return discoveryClient	.getInstances(ORGANIZATION_SERVICE_APP_NAME)
								.stream()
								.map(ServiceInstance::getUri)
								.map(URI::toString)
								.map(uri -> String.format(ORGANIZACION_SERVICE_URI_TEMPLATE, uri, organizationId))
								.map(fullUrl -> callAPI(fullUrl, organizationId))
								.filter(resp -> HttpStatus.OK.equals(resp.getStatusCode()))
								.map(ResponseEntity::getBody)
								.findFirst();

	}

	public static ResponseEntity<Organization> callAPI(String fullUrl, String organizationId) {
		try {
			return new RestTemplate().exchange(fullUrl, HttpMethod.GET, null, Organization.class, organizationId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
