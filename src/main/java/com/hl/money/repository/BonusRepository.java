package com.hl.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hl.money.entity.Bonus;

public interface BonusRepository extends JpaRepository<Bonus, Integer> {

}
