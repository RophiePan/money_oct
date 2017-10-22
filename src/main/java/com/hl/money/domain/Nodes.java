package com.hl.money.domain;

import java.util.ArrayList;
import java.util.List;

import com.hl.money.entity.UserNode;

public class Nodes {
	private UserNode userNode;

	private List<Nodes> children;

	public UserNode getUserNode() {
		return this.userNode;
	}

	public void setUserNode(final UserNode userNode) {
		this.userNode = userNode;
	}

	public List<Nodes> getChildren() {
		if (null == this.children) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

}
