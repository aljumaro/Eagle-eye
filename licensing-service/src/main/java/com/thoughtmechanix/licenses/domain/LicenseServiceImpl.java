package com.thoughtmechanix.licenses.domain;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.boundary.dto.Organization;
import com.thoughtmechanix.licenses.boundary.servicelookup.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.boundary.servicelookup.OrganizationFeignClient;
import com.thoughtmechanix.licenses.boundary.servicelookup.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.infrastructure.ConfigService;
import com.thoughtmechanix.licenses.infrastructure.SlowCallsUtils;

@Service
public class LicenseServiceImpl implements LicenseService {

	private LicenseRepository licenseRepository;
	private ConfigService configService;
	private OrganizationDiscoveryClient organizationDiscoveryClient;
	private OrganizationRestTemplateClient organizationRestTemplateClient;
	private OrganizationFeignClient organizationFeignClient;

	@Override
	@HystrixCommand(
		commandProperties = {
			@HystrixProperty(
				name = "execution.isolation.thread.timeoutInMilliseconds",
				value = "1000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "25"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
			@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
		},
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		},
		fallbackMethod = "fMfindByOrganizationId",
		threadPoolKey = "findByOrganizationIdThreadPool"
	) //Circuit-breaker proxy wraps the method call
	public List<License> findByOrganizationId(String organizationId) {
		
		System.out.println(Calendar.getInstance().toString() + "findByOrganizationId - slowing");
		
		SlowCallsUtils.randomRunSlow();
		
		System.out.println("findByOrganizationId - dbCall");
		
		return licenseRepository.findByOrganizationId(organizationId);
	}

	@Override
	public License findByOrganizationIdAndId(String organizationId, String id) {
		License license = licenseRepository.findByOrganizationIdAndId(organizationId, id);
		if (license != null) {
			license.setComment(configService.getTracerProperty());
		}
		return license;
	}

	@Override
	public License save(License license) {
		if (StringUtils.isEmpty(license.getId())) {
			license.withId(UUID	.randomUUID()
								.toString());
		}
		return licenseRepository.save(license);
	}

	@Override
	public License getLicense(String organizationId, String id, String clientType) {
		License license = findByOrganizationIdAndId(organizationId, id);

		license.setOrganization(retrieveOrganizationInfo(organizationId, clientType));

		return license;
	}

	@HystrixCommand
	private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
		
		Optional<Organization> res = Optional.<Organization> empty();
		
		switch (clientType) {
		case "discovery":
			res = organizationDiscoveryClient.getOrganizationInfo(organizationId);
		case "restTemplate":
			res = organizationRestTemplateClient.getOrganizationInfo(organizationId);
		case "feign":
			res = Optional.<Organization> of(organizationFeignClient.getOrganizationInfo(organizationId));
		}
		
		return res.orElse(null);
	}
	
	private List<License> fMfindByOrganizationId(String organizationId) {
		return Arrays.asList(new License()	.withId("00000000-0000-0000-0000-000000000000")
											.withOrganizationId(organizationId)
											.withComment("Not able to retrieve the Licenses for: " + organizationId));
	}
	
	/*------------spring optional autowired setter methods---------------*/
	
	@Autowired
	public void setOrganizationFeignClient(OrganizationFeignClient organizationFeignClient) {
		this.organizationFeignClient = organizationFeignClient;
	}

	@Autowired
	public void setOrganizationRestTemplateClient(OrganizationRestTemplateClient organizationRestTemplateClient) {
		this.organizationRestTemplateClient = organizationRestTemplateClient;
	}

	@Autowired
	public void setOrganizationDiscoveryClient(OrganizationDiscoveryClient organizationDiscoveryClient) {
		this.organizationDiscoveryClient = organizationDiscoveryClient;
	}

	@Autowired
	public void setLicenseRepository(LicenseRepository licenseRepository) {
		this.licenseRepository = licenseRepository;
	}

	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

}
