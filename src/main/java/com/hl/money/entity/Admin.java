package com.hl.money.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Admin {
	@Id
	@GeneratedValue
	private Integer adminId;
	private String adminAccount;
	private String password;
	private String adminName;
	private String comment;

	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(final Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminAccount() {
		return this.adminAccount;
	}

	public void setAdminAccount(final String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(final String adminName) {
		this.adminName = adminName;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
}
