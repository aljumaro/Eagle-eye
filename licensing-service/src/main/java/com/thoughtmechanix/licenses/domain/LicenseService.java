package com.thoughtmechanix.licenses.domain;

import java.util.List;

public interface LicenseService {
	
	public List<License> findByOrganizationId(String organizationId);

	public License findByOrganizationIdAndId(String organizationId, String id);
	
	public License save(License license);

}
