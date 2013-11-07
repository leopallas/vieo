package org.workin.unit.orm.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.workin.unit.orm.domain.IdEntity;
import org.workin.utils.reflection.ConvertUtils;

import com.google.common.collect.Lists;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系. 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 
 * @author calvin
 */
@Entity
@Table(name = "USER_TABLE")
@NamedQueries({ @NamedQuery(name = "findAllUser", query = "select p from User p"),
		@NamedQuery(name = "findUserById", query = "select p from User p where p.id=?"),
		@NamedQuery(name = "findUserByNameAndPasswd", query = "select p from User p where p.name=? and p.password=?"),
		@NamedQuery(name = "findUserByName", query = "select p from User p where p.name=:name") })
public class User extends IdEntity {
	private static final long serialVersionUID = 7677684392852305023L;
	private String loginName;
	private String password;
	private String name;
	private String email;
	private List<Role> roleList = Lists.newArrayList();

	@Column(name = "LOGIN_NAME", length = 50, nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	@Transient
	public String getRoleNames() {
		return ConvertUtils.convertElementPropertyToString(roleList, "name", ", ");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ConvertUtils.convertElementPropertyToList(roleList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}