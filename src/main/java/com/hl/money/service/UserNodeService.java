package com.hl.money.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.hl.money.domain.Nodes;
import com.hl.money.entity.UserNode;
import com.hl.money.repository.UserNodeRepository;

@Service
public class UserNodeService {

	@Autowired
	private UserNodeRepository userNodeRepository;

	public List<UserNode> getUserNodeByUserId(final int userId) {
		final UserNode userNode = new UserNode();
		userNode.setUserId(userId);
		final Example<UserNode> e = Example.of(userNode);
		return this.userNodeRepository.findAll(e);
	}

	public Nodes getNodes(final Integer userId) {
		final List<UserNode> allNodes = this.userNodeRepository.findAll();
		final UserNode userNode = new UserNode();
		userNode.setUserId(userId);
		final Example<UserNode> e = Example.of(userNode);
		final UserNode parentNode = this.userNodeRepository.findOne(e);
		final Nodes nodes = this.bulidNodes(allNodes, parentNode);
		return nodes;
	}

	private Nodes bulidNodes(final List<UserNode> allNodes, final UserNode parentNode) {
		final Nodes parentNodes = new Nodes();
		parentNodes.setUserNode(parentNode);
		this.findChildNode(allNodes, parentNodes);
		return parentNodes;
	}

	private void findChildNode(final List<UserNode> allNodes, final Nodes parentNodes) {
		if (this.isHasChild(allNodes, parentNodes.getUserNode())) {
			final List<UserNode> childNodes = this.getUserNode(allNodes, parentNodes.getUserNode());
			for (final UserNode userNode : childNodes) {
				final Nodes node = new Nodes();
				node.setUserNode(userNode);
				parentNodes.getChildren().add(node);
				this.findChildNode(allNodes, node);
			}
		}
	}

	private boolean isHasChild(final List<UserNode> allNodes, final UserNode parentNode) {
		for (final UserNode userNode : allNodes) {
			if (userNode.getParentId().intValue() == parentNode.getUserId().intValue()) {
				return true;
			}
		}
		return false;
	}

	private List<UserNode> getUserNode(final List<UserNode> allNodes, final UserNode parentNode) {
		final List<UserNode> childNodes = new ArrayList<>();
		for (final UserNode userNode : allNodes) {
			if (userNode.getParentId().intValue() == parentNode.getUserId().intValue()) {
				childNodes.add(userNode);
			}
		}
		return childNodes;
	}

}
