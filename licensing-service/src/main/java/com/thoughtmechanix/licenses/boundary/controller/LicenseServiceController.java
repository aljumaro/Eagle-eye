package com.thoughtmechanix.licenses.boundary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.licenses.domain.License;

@RestController
@RequestMapping("v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

	@GetMapping(value = "/{licenseId}")
	public License getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return new License().withId(licenseId).withOrganizationId(organizationId).withProductName("Teleco")
				.withLicenseType("Seat");
	}
}
