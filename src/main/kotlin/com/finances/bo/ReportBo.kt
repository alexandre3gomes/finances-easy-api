package com.finances.bo

import com.finances.dto.report.CategoryAggregValuesDto
import com.finances.entity.BudgetPeriods
import com.finances.entity.Income
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.repository.IncomeRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ReportBo(
    var budgetRepository: BudgetRepository,
    var categoryRepository: CategoryRepository,
    var incomeRepository: IncomeRepository
) {

    fun byCategory(budgetId: Int?): List<CategoryAggregValuesDto> {
        val ret: MutableList<CategoryAggregValuesDto> = ArrayList()
        val categories = categoryRepository.getCategoriesByBudget(budgetId!!)
        for (cat in categories) {
            val catValues = CategoryAggregValuesDto(cat, budgetRepository.getValuesByCategories(budgetId, cat.id))
            ret.add(catValues)
        }
        return ret
    }

    fun incomeByPeriod(budgetId: Int?): List<BigDecimal> {
        val ret: MutableList<BigDecimal> = ArrayList()
        val periods: List<BudgetPeriods?> = budgetRepository.getPeriodsByBudget(budgetId)
        for (per in periods) {
            val listIncome: List<Income> = incomeRepository.findByDateBetween(
                per!!.startDate,
                per.endDate
            )
            if (listIncome.isNotEmpty()) {
                ret.add(listIncome.map { it.value }.fold(BigDecimal.ZERO, BigDecimal::add))
            } else {
                ret.add(BigDecimal.ZERO)
            }
        }
        return ret
    }
}
