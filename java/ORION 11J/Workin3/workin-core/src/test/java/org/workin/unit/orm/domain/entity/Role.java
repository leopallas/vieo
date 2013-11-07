package org.workin.unit.orm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.workin.unit.orm.domain.IdEntity;

/**
 * 角色.
 * 
 * 注释见{@link User}.
 * 
 * @author calvin
 */
@Entity
@Table(name = "ROLE_TABLE")
public class Role extends IdEntity {

	private static final long serialVersionUID = 8710586727429501876L;
	private String name;

	public Role() {

	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(name = "NAME", length = 50, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
