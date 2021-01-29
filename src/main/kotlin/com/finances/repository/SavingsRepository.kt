package com.finances.repository

import com.finances.entity.Savings
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.transaction.Transactional

@Repository
@Transactional
interface SavingsRepository : JpaRepositoryImplementation<Savings, Int> {

    @Query("SELECT SUM(s.value) FROM Savings s")
    fun sumSavings(): BigDecimal
}
