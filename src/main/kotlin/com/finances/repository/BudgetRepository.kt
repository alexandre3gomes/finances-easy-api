package com.finances.repository

import com.finances.dto.report.PeriodValueDto
import com.finances.entity.Budget
import com.finances.entity.BudgetPeriods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BudgetRepository : JpaRepository<Budget, Int> {

    @Query("select bp from BudgetPeriods bp where bp.budget.id = :budgetId order by bp.budgetPeriodsId.idPeriod")
    fun getPeriodsByBudget(budgetId: Int?): List<BudgetPeriods>

    @Query("select bp from BudgetPeriods bp where :now between bp.startDate and bp.endDate")
    fun getPeriodsByDate(now: LocalDateTime?): BudgetPeriods?

    @Query(
        "select bp.startDate as startDate, bp.endDate as endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue from BudgetPeriods bp" +
            " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id" +
            " left join Category cat on bc.category = cat.id" +
            " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate" +
            " where b.id = :budgetId and cat.id = :categoryId" +
            " group by bp.budgetPeriodsId.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.budgetPeriodsId.idPeriod"
    )
    fun getValuesByCategories(budgetId: Int?, categoryId: Int?): List<PeriodValueDto>

    @Query(
        "select bp.startDate as startDate, bp.endDate as endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue from BudgetPeriods bp" +
            " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id" +
            " left join Category cat on bc.category = cat.id" +
            " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate" +
            " where cat.id = :categoryId and :now between bp.startDate and bp.endDate " +
            " group by bp.budgetPeriodsId.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.budgetPeriodsId.idPeriod"
    )
    fun getAggregateValueByDate(now: LocalDateTime?, categoryId: Int?): List<PeriodValueDto>
}
