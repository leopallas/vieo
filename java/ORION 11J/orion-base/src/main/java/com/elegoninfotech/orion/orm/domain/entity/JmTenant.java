package com.elegoninfotech.orion.orm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.elegoninfotech.orion.orm.domain.AuditableEntity;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Table(name = "JM_TENANT")
@Entity
public class JmTenant extends AuditableEntity {

	private static final long serialVersionUID = 4538807089668434785L;

	private String tenantCode;
	private String tenantName;
	private String tenantStatus;

	@Column(name = "TENANT_CODE", length = 50)
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	@Column(name = "TENANT_NAME", length = 50)
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	@Column(name = "TENANT_STATUS", length = 50)
	public String getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

}
