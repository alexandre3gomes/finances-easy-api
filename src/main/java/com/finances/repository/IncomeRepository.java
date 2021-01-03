package com.finances.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finances.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

	Optional<List<Income>> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
