package com.thoughtmechanix.licenses.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.thoughtmechanix.licenses.boundary.dto.Organization;
import com.thoughtmechanix.licenses.boundary.servicelookup.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.infrastructure.ConfigService;

@Service
public class LicenseServiceImpl implements LicenseService {

	private LicenseRepository licenseRepository;
	private ConfigService configService;
	private OrganizationDiscoveryClient organizationDiscoveryClient;

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

	@Override
	public List<License> findByOrganizationId(String organizationId) {
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
			license.withId(UUID.randomUUID().toString());
		}
		return licenseRepository.save(license);
	}

	@Override
	public License getLicense(String organizationId, String id, String clientType) {
		License license = findByOrganizationIdAndId(organizationId, id);
		
		license.setOrganization(retrieveOrganizationInfo(organizationId, clientType));
		
		return license;
	}

	private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
		if (clientType.equals("discovery")) {
			Optional<Organization> organizationInfo = organizationDiscoveryClient.getOrganizationInfo(organizationId);
			if (organizationInfo.isPresent()) {
				return organizationInfo.get();
			}
		}
		
		return null;
	}

}
