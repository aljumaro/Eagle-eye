package com.thoughtmechanix.licenses.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.thoughtmechanix.licenses.boundary.dto.Organization;

@Entity
@Table(name = "LICENSE")
public class License {

	@Id
	private String id;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "LICENSE_TYPE")
	private String licenseType;

	@Column(name = "ORGANIZATION_ID")
	private String organizationId;

	@Transient
	private String comment;

	@Transient
	private Organization organization;

	public License withId(String id) {
		this.id = id;
		return this;
	}

	public License withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public License withLicenseType(String licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public License withOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	public License withComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
