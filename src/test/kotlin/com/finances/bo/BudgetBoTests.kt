package com.finances.bo

import com.finances.enums.BreakpointEnum
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.util.BuildMockDataUtil
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.AssertionErrors
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class BudgetBoTests(
    @InjectMocks
    private val budgetBo: BudgetBo
) {

    @Mock
    private val budgetRepo: BudgetRepository? = null

    @Mock
    private val catRepo: CategoryRepository? = null

    fun createBudgetMonthlyBreakpoint() {
        val budget = BuildMockDataUtil.buildBudget(BreakpointEnum.MONTHLY)
        val newBudget = budgetBo.buildPeriods(budget)
        AssertionErrors.assertTrue("Periods needs to have full month", newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) -> startDate.month == endDate.month })
    }

    fun createBudgetWeeklyBreakpoint() {
        val budget = BuildMockDataUtil.buildBudget(BreakpointEnum.WEEKLY)
        val newBudget = budgetBo.buildPeriods(budget)
        AssertionErrors.assertTrue("Periods needs to have a duration of one week", newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) -> endDate.minusWeeks(1).plusDays(1).withHour(0).withMinute(0).withSecond(0).compareTo(startDate) == 0 })
    }

    fun testGetActualValues() {
        Mockito.`when`(catRepo!!.getCategoriesByPeriod(ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildCategories())
        Mockito.`when`(budgetRepo!!.getAggregateValueByDate(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues())
        AssertionErrors.assertTrue("The sum of actual values is not 200", budgetBo.actualBalance.stream().mapToDouble { (_, periodValue) -> periodValue!![0].actualValue!!.toDouble()  }.sum() == 200.0)
    }
}