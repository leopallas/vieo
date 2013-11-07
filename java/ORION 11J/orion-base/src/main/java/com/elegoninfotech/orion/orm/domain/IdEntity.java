package com.elegoninfotech.orion.orm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class IdEntity implements Serializable {

	private static final long serialVersionUID = -4590528387858663443L;

	protected String id;

	private long version;

	@Id
	@GeneratedValue(generator = "UIDGenerator")
	@GenericGenerator(name = "UIDGenerator", strategy = "com.elegoninfotech.orion.orm.domain.UIDGenerator")
	@Column(name = "SYS_ID", length = 18, nullable = false, unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Version
	@Column(name = "VERSION")
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
