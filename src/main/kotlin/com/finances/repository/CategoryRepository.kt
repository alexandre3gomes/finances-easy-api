package com.finances.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.finances.dto.report.PeriodValueDto
import com.finances.entity.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import java.math.BigDecimal
import java.time.LocalDateTime

interface CategoryRepository : JpaRepository<Category, Int> {

    @Query("select bc.category from BudgetCategories bc where bc.budget.id = :budgetId")
    fun getCategoriesByBudget(budgetId: Int): List<Category>

    @Query("select bc.category from BudgetCategories bc "
            + " left join BudgetPeriods bp on bc.budget.id = bp.budget.id "
            + " where :now between bp.startDate and bp.endDate")
    fun getCategoriesByPeriod(now: LocalDateTime): List<Category>
}