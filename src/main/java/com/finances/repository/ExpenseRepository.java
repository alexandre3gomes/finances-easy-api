package com.finances.repository;

import com.finances.entity.Expense;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

    Optional<List<Expense>> findByExpireAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
