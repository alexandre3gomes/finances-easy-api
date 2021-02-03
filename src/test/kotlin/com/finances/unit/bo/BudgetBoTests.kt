package com.finances.unit.bo

import com.finances.bo.BudgetBo
import com.finances.enums.BreakpointEnum
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(BudgetBo::class)
class BudgetBoTests() {

    @Autowired
    private lateinit var budgetBo: BudgetBo

    @MockkBean
    private lateinit var budgetRepo: BudgetRepository

    @MockkBean
    private lateinit var catRepo: CategoryRepository

    @Test
    fun createBudgetMonthlyBreakpoint() {
        val budget = BuildMockDataUtil.buildListOfBudgets(BreakpointEnum.MONTHLY)
        val newBudget = budgetBo.buildPeriods(budget[0])
        assertThat(
            newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) -> startDate.month == endDate.month }
        ).isTrue
    }

    @Test
    fun createBudgetWeeklyBreakpoint() {
        val budget = BuildMockDataUtil.buildListOfBudgets(BreakpointEnum.WEEKLY)
        val newBudget = budgetBo.buildPeriods(budget[0])
        assertThat(
            newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) ->
                    endDate.minusWeeks(1).plusDays(1).withHour(0).withMinute(0).withSecond(0).compareTo(startDate) == 0
                }
        ).isTrue
    }

    @Test
    fun testGetActualValues() {
        every { catRepo.getCategoriesByPeriod(any()) } returns BuildMockDataUtil.buildListOfCategories()
        every { budgetRepo.getAggregateValueByDate(any(), any()) } returns BuildMockDataUtil.buildPeriodValues()
        val actual = budgetBo.actualBalance.stream().mapToDouble { (_, periodValue) -> periodValue!![0].actualValue!!.toDouble() }.sum()
        assertThat(actual == 300.0).isTrue
    }
}
