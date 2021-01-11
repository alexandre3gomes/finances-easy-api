package com.finances.repository

import com.finances.entity.Expense
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface ExpenseRepository : JpaRepositoryImplementation<Expense, Int> {

    fun findByExpireAtBetween(startDate: LocalDateTime, endDate: LocalDateTime): Optional<List<Expense>>
}