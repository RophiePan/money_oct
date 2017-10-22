package com.hl.money.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Bonus {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "Primary Key")
	private Integer bonusId;

	@NotNull
	private Integer userId;

	@NotBlank
	private String userName;

	@NotNull
	private Integer amount;

	@NotBlank
	private String bonusType;

	@NotNull
	private Date awardDate;

	@NotNull
	private int status;

	private String comments;

	public Integer getBonusId() {
		return this.bonusId;
	}

	public void setBonusId(final Integer bonusId) {
		this.bonusId = bonusId;
	}

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

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(final Integer amount) {
		this.amount = amount;
	}

	public String getBonusType() {
		return this.bonusType;
	}

	public void setBonusType(final String bonusType) {
		this.bonusType = bonusType;
	}

	public Date getAwardDate() {
		return this.awardDate;
	}

	public void setAwardDate(final Date awardDate) {
		this.awardDate = awardDate;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
