package com.finances.repository

import com.finances.entity.Income
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository

interface IncomeRepository : JpaRepository<Income, Int> {

    fun findByDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Income>
}
