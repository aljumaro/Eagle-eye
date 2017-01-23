package com.thoughtmechanix.organization.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationRepository organizationRepository;

	@Autowired
	public void setOrganizationRepository(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public Organization save(Organization organization) {
		if (StringUtils.isEmpty(organization.getId())) {
			organization.setId(UUID.randomUUID().toString());
		}
		return organizationRepository.save(organization);
	}

	@Override
	public Organization find(String id) {
		return organizationRepository.findOne(id);
	}

	@Override
	public List<Organization> findAll() {
		return (List<Organization>) organizationRepository.findAll();
	}

}
