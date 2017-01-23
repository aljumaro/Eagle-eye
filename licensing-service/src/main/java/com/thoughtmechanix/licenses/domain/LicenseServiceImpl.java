package com.thoughtmechanix.licenses.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.thoughtmechanix.licenses.infrastructure.ConfigService;

@Service
public class LicenseServiceImpl implements LicenseService {

	private LicenseRepository licenseRepository;
	private ConfigService configService;

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

}
