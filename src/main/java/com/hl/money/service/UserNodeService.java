package com.hl.money.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.hl.money.entity.UserNode;
import com.hl.money.repository.UserNodeRepository;

@Service
public class UserNodeService {

	@Autowired
	private UserNodeRepository userNodeRepository;

	public List<UserNode> getUserNodeByUserId(final int userId) {
		final UserNode userNode = new UserNode();
		userNode.setUserId(userId);
		final Example e = Example.of(userNode);
		return this.userNodeRepository.findAll(e);
	}

}
