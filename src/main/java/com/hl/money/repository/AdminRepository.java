package com.hl.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
