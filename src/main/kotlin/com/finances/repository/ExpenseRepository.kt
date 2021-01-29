package com.finances.repository

import com.finances.entity.Expense
import java.time.LocalDateTime
import javax.transaction.Transactional
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository

@Repository
@Transactional
interface ExpenseRepository : JpaRepositoryImplementation<Expense, Int> {

    fun findByExpireAtBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Expense>
}
