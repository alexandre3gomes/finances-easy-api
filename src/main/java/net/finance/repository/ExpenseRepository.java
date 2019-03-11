package net.finance.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import net.finance.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

}
