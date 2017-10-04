package com.hl.money.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hl.money.entity.User;
import com.hl.money.enums.UserStatus;
import com.hl.money.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByIdPassword(final User user) {
		return this.userRepository.findUserByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

	public int checkUser(final int userId) {
		return this.userRepository.findUserByUserId(userId).size();
	}

	@Transactional
	public User createUser(final User u) {
		u.setRegisterDate(new Date());
		u.setUserStatus(UserStatus.UNAUDIT.getValue());
		return this.userRepository.save(u);
	}

}
