package com.elegoninfotech.orion.orm.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class Contact implements Serializable {

	private static final long serialVersionUID = -1911037867909117364L;

	private Integer id;

	private String firstname;

	private String lastname;

	private String email;

	private String telephone;

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	@Column(name = "LASTNAME")
	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}