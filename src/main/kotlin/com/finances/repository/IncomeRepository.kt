package com.finances.repository

import com.finances.entity.Income
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface IncomeRepository : JpaRepository<Income, Int> {

    fun findByDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Income>
}
