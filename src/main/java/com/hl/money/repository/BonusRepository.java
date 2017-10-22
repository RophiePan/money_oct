package com.hl.money.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.Bonus;

public interface BonusRepository extends JpaRepository<Bonus, Integer> {
	
	Page<Bonus> findAllByUserId(int userId, Pageable page);

}
