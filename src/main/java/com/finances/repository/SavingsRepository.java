package com.finances.repository;

import com.finances.entity.Savings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
@Transactional
public interface SavingsRepository extends JpaRepositoryImplementation<Savings, Integer> {

    @Query("SELECT SUM(s.value) FROM Savings s")
    BigDecimal getSumSavings();
}
