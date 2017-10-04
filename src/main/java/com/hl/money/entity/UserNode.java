package com.hl.money.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class UserNode {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "Primary Key")
	private int nodeId;
	@NotNull
	private int userId;
	@NotNull
	@Column(columnDefinition = "推荐人的Id 也是此人的根节点")
	private int rootNodeId;
	@NotNull
	@Column(columnDefinition = "此人在根节点的哪个分支上 1 代表左分支， 2 代表右分支")
	private int rootSubBranch;
	@NotNull
	@Column(columnDefinition = "父节点的Id")
	private int parentId;
	@NotNull
	@Column(columnDefinition = "父节点的分支，1 代表左分支，2 代表右分支。 每个分支最多含有9层，9个节点（包含自己）")
	private int subBranch;
	@NotNull
	@Column(columnDefinition = "节点创建日期")
	private Date createDate;
	@NotNull
	@Column(columnDefinition = "节点层数，从1开始")
	private int level;
	
	private String comment;

	public int getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(final int nodeId) {
		this.nodeId = nodeId;
	}

	public int getSubBranch() {
		return this.subBranch;
	}

	public void setSubBranch(final int subBranch) {
		this.subBranch = subBranch;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	public int getRootNodeId() {
		return this.rootNodeId;
	}

	public void setRootNodeId(final int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	public int getRootSubBranch() {
		return this.rootSubBranch;
	}

	public void setRootSubBranch(final int rootSubBranch) {
		this.rootSubBranch = rootSubBranch;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(final int parentId) {
		this.parentId = parentId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
}
