package com.hl.money.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findUserByUserIdAndPassword(int userId, String password);
	
	public List<User> findUserByUserName(String userName);
	
}
