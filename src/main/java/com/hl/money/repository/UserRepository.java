package com.hl.money.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public List<User> findUserByUserId(int userId);

	public User findUserByUserIdAndPassword(int userId, String password);
}
