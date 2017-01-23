package com.thoughtmechanix.organization.domain;

import java.util.List;

public interface OrganizationService {

	Organization save(Organization organization);
	
	Organization find(String id);
	
	List<Organization> findAll();
}
