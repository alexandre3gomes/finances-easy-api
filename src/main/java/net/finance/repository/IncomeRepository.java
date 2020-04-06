package net.finance.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

	Optional<List<Income>> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
