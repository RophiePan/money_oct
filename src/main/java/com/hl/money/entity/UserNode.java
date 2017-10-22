package com.hl.money.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class UserNode {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "Primary Key")
	private Integer nodeId;
	@NotNull
	private Integer userId;
	@NotBlank
	private String userName;
	@NotNull
	@Column(columnDefinition = "推荐人的Id 也是此人的根节点")
	private Integer rootNodeId;
	@NotNull
	@Column(columnDefinition = "父节点的Id")
	private Integer parentId;
	@NotBlank
	private String parentName;
	@NotNull
	@Column(columnDefinition = "父节点的分支，1 代表左分支，2 代表右分支。 每个分支最多含有9层，9个节点（包含自己）")
	private Integer subBranch;
	@NotNull
	@Column(columnDefinition = "节点创建日期")
	private Date createDate;
	@NotNull
	@Column(columnDefinition = "节点层数，从1开始")
	private Integer level;

	private String comment;

	public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(final Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getSubBranch() {
		return this.subBranch;
	}

	public void setSubBranch(final Integer subBranch) {
		this.subBranch = subBranch;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRootNodeId() {
		return this.rootNodeId;
	}

	public void setRootNodeId(final Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(final Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(final Integer level) {
		this.level = level;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
