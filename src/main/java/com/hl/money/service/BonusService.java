package com.hl.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hl.money.entity.Bonus;
import com.hl.money.repository.BonusRepository;

@Service
public class BonusService {
	@Autowired
	private BonusRepository bonusRepository;

	public Page<Bonus> getAllBonus(final int currentPage, final int size) {
		final Sort sort = new Sort(Sort.Direction.DESC, "awardDate");
		final Pageable page = new PageRequest(currentPage - 1, size, sort);
		return this.bonusRepository.findAll(page);
	}

	public Page<Bonus> getBonusById(final int userId, final int currentPage, final int size) {
		final Sort sort = new Sort(Sort.Direction.DESC, "awardDate");
		final Pageable page = new PageRequest(currentPage - 1, size, sort);
		return this.bonusRepository.findAllByUserId(userId, page);

	}
}
