package com.elegoninfotech.orion.orm.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@MappedSuperclass
public class SAASEntity extends AuditableEntity {

	private static final long serialVersionUID = -1766617394560578343L;

	private String tenantCode;
	private String tenantName;

	@Column(name = "TENANT_CODE", length = 50, nullable = false, updatable = false)
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	@Column(name = "TENANT_NAME", length = 50, nullable = false, updatable = false)
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

}
