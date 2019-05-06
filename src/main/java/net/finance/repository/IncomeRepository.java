package net.finance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

	List<Income> findByDateBetween(Date startDate, Date endDate);

}
