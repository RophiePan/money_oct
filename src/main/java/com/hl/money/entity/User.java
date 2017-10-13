package com.hl.money.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	@NotBlank
	private String userName;
	@NotBlank
	private String password;
	@NotBlank
	private String idCard;
	@NotBlank
	private String bankCard;
	@NotBlank
	private String bankName;
	@NotBlank
	private String phoneNumber;
	@NotNull
	private Integer recommendId;
	@NotNull
	private Integer userStatus;
	@NotNull
	private Date registerDate; // 状态0为未审核，状态1为已审核
	private Date auditDate;
	private String comments;

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(final String idCard) {
		this.idCard = idCard;
	}

	public String getBankCard() {
		return this.bankCard;
	}

	public void setBankCard(final String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getRecommendId() {
		return this.recommendId;
	}

	public void setRecommendId(final Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(final Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(final Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(final Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}
}
