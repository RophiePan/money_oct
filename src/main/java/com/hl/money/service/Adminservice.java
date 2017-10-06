package com.hl.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.hl.money.entity.Admin;
import com.hl.money.repository.AdminRepository;

@Service
public class Adminservice {

	@Autowired
	private AdminRepository adminRepository;

	public Admin getUserByIdPassword(final Admin admin) {
		final Example<Admin> example = Example.of(admin);
		return this.adminRepository.findOne(example);
	}

}
