package com.finances.bo

import com.finances.enums.BreakpointEnum
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.util.BuildMockDataUtil
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.util.AssertionErrors
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class BudgetBoTests() {

    @Autowired
    private lateinit var budgetBo: BudgetBo

    @MockBean
    private lateinit var budgetRepo: BudgetRepository

    @MockBean
    private lateinit var catRepo: CategoryRepository

    @Test
    fun createBudgetMonthlyBreakpoint() {
        val budget = BuildMockDataUtil.buildBudget(BreakpointEnum.MONTHLY)
        val newBudget = budgetBo.buildPeriods(budget)
        AssertionErrors.assertTrue("Periods needs to have full month", newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) -> startDate.month == endDate.month })
    }

    @Test
    fun createBudgetWeeklyBreakpoint() {
        val budget = BuildMockDataUtil.buildBudget(BreakpointEnum.WEEKLY)
        val newBudget = budgetBo.buildPeriods(budget)
        AssertionErrors.assertTrue("Periods needs to have a duration of one week", newBudget.periods.stream()
                .allMatch { (_, _, startDate, endDate) -> endDate.minusWeeks(1).plusDays(1).withHour(0).withMinute(0).withSecond(0).compareTo(startDate) == 0 })
    }

    @Test
    fun testGetActualValues() {
        every { catRepo.getCategoriesByPeriod(ArgumentMatchers.any(LocalDateTime::class.java)) } returns BuildMockDataUtil.buildCategories()
        every { budgetRepo.getAggregateValueByDate(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.anyInt()) } returns BuildMockDataUtil.buildPeriodValues()
        AssertionErrors.assertTrue("The sum of actual values is not 200", budgetBo.actualBalance.stream().mapToDouble { (_, periodValue) -> periodValue!![0].actualValue!!.toDouble()  }.sum() == 200.0)
    }
}