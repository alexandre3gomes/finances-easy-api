package com.finances.bo

import com.finances.dto.BudgetDTO
import com.finances.dto.report.CategoryAggregValuesDto
import com.finances.entity.Budget
import com.finances.entity.BudgetPeriods
import com.finances.enums.BreakpointEnum
import com.finances.mapper.toDTO
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.TreeSet

@Service
class BudgetBo(private val budgetRepository: BudgetRepository, private val categoryRepository: CategoryRepository) {

    fun create(budget: Budget): BudgetDTO {
        return budgetRepository.save(buildPeriods(budget)).toDTO()
    }

    fun delete(id: Int) {
        budgetRepository.deleteById(id)
    }

    fun get(id: Int): BudgetDTO {
        return budgetRepository.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(pageRequest: PageRequest): Page<BudgetDTO> {
        return budgetRepository.findAll(pageRequest).map(Budget::toDTO)
    }

    fun update(budget: Budget): BudgetDTO {
        budgetRepository.deleteById(budget.id)
        budget.id = 0
        return budgetRepository.save(buildPeriods(budget)).toDTO()
    }

    val actualBalance: List<CategoryAggregValuesDto>
        get() {
            val ret: MutableList<CategoryAggregValuesDto> = ArrayList()
            val categories = categoryRepository.getCategoriesByPeriod(LocalDateTime.now())
            for (cat in categories) {
                val catAg = CategoryAggregValuesDto(cat, budgetRepository.getAggregateValueByDate(LocalDateTime.now(), cat.id))
                ret.add(catAg)
            }
            return ret
        }

    fun buildPeriods(budget: Budget): Budget {
        budget.periods = splitInMonths(budget)
        budget.categories.forEach { it.budget = budget }
        return budget
    }

    companion object {
        private fun splitInMonths(budget: Budget): Set<BudgetPeriods> {
            val periods: MutableSet<BudgetPeriods> = TreeSet()
            var sDate = budget.startDate
            val eDate = budget.endDate
            var idPeriod = 0
            while (sDate.isBefore(eDate)) {
                val budPed = BudgetPeriods(budget, idPeriod)
                budPed.budget = budget
                budPed.startDate = sDate
                var finalDate: LocalDateTime?
                if (budget.breakperiod == BreakpointEnum.MONTHLY.id) {
                    finalDate = sDate.plusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59)
                    sDate = finalDate.plusDays(1).withHour(0).withMinute(0)
                        .withSecond(0)
                } else {
                    sDate = sDate.withHour(0).withMinute(0).withSecond(0).plusWeeks(1)
                    finalDate = sDate.withHour(23).withMinute(59).withSecond(59).plusDays(-1)
                }
                budPed.endDate = finalDate
                periods.add(budPed)
                idPeriod++
            }
            return periods
        }
    }
}
