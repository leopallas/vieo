package com.elegoninfotech.orion.orm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@MappedSuperclass
public class AuditableEntity extends IdEntity {

	private static final long serialVersionUID = -5635605799094539384L;

	private String createBy;
	private Date createTime;
	private String lastModifyBy;
	private Date lastModifyTime;

	@Column(name = "CREATE_BY", length = 50, updatable = false)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATE_TIME", updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LAST_MODIFY_BY", length = 50, insertable = false)
	public String getLastModifyBy() {
		return lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "LAST_MODIFY_TIME", updatable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
