package net.finance.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import net.finance.entity.Category;
import net.finance.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

	Page<Expense> findByCategory(Category category, Pageable page);

	Page<Expense> findByExpireAtBetween(Date startDate, Date endDate, Pageable page);

	Page<Expense> findByCategoryAndExpireAtBetween(Category category, Date startDate, Date endDate, Pageable page);

}
