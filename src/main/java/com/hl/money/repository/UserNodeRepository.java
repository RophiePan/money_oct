package com.hl.money.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.UserNode;

public interface UserNodeRepository extends JpaRepository<UserNode, Integer> {
	List<UserNode> findUserNodeByUserId(int userId);
}
