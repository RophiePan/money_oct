package com.hl.money.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public List<User> checkUser(final int userId) {
		final User u = new User();
		u.setUserId(userId);
		u.setUserStatus(UserStatus.AUDITED.getValue());
		final Example<User> e = Example.of(u);
		return this.userRepository.findAll(e);
	}

	public List<User> getUsersByUserName(final String name) {
		return this.userRepository.findUserByUserName(name);
	}

	public Page<User> getUsersByUserStatus(final int status, final int currentPage, final int size) {
		final User u = new User();
		u.setUserStatus(status);
		final Example<User> e = Example.of(u);
		final Sort sort = new Sort(Sort.Direction.ASC, "userId");
		final Pageable page = new PageRequest(currentPage - 1, size, sort);
		return this.userRepository.findAll(e, page);
	}

	public long getCountByUserStatus(final int status) {
		final User u = new User();
		u.setUserStatus(status);
		final Example<User> e = Example.of(u);
		return this.userRepository.count(e);
	}

	@Transactional
	public User createUser(final User u) {
		u.setRegisterDate(new Date());
		u.setUserStatus(UserStatus.UNAUDIT.getValue());
		return this.userRepository.save(u);
	}

	public void deleteUserById(final Integer userId) {
		this.userRepository.delete(userId);
	}

}
