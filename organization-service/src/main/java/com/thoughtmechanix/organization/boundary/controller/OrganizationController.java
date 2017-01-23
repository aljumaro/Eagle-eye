package com.thoughtmechanix.organization.boundary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.organization.domain.Organization;
import com.thoughtmechanix.organization.domain.OrganizationService;

@RestController
@RequestMapping("v1/organizations/")
public class OrganizationController {

	private OrganizationService organizationService;

	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@GetMapping
	public List<Organization> findAll() {
		return organizationService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Organization findById(@PathVariable("id") String id) {
		return organizationService.find(id);
	}
	
	@PostMapping
	public Organization save(@RequestBody Organization organization) {
		return organizationService.save(organization);
	}
	
}
