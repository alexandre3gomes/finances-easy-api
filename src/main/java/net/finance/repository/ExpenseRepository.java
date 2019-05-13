package net.finance.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import net.finance.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

	Optional<List<Expense>> findByExpireAtBetween(Date startDate, Date endDate);

}
