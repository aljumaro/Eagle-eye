package com.thoughtmechanix.licenses.boundary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.licenses.domain.License;
import com.thoughtmechanix.licenses.domain.LicenseService;

@RestController
@RequestMapping("v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

	private LicenseService licenseService;

	@Autowired
	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}
	
	@GetMapping(value = "/")
	public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
		return licenseService.findByOrganizationId(organizationId);
	}

	@GetMapping(value = "/{licenseId}")
	public License getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return licenseService.findByOrganizationIdAndId(organizationId, licenseId);
	}
	
	@GetMapping(value = "/{licenseId}/{clientType}")
	public License getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId,
			@PathVariable("clientType") String clientType) {
		return licenseService.getLicense(organizationId, licenseId, clientType);
	}
	
	@PostMapping
	public License saveLicense(@PathVariable("organizationId") String organizationId, @RequestBody License license) {
		license.setOrganizationId(organizationId);
		return licenseService.save(license);
	}

}
