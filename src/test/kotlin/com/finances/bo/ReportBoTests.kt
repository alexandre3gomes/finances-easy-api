package com.finances.bo

import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.repository.IncomeRepository
import com.finances.util.BuildMockDataUtil
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.AssertionErrors
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ReportBoTests {
    @Mock
    var incomeRepository: IncomeRepository? = null

    @Mock
    var budgetRepository: BudgetRepository? = null

    @Mock
    var categoryRepository: CategoryRepository? = null

    @InjectMocks
    var reportBo: ReportBo? = null

    fun testByCategoryWithReturn() {
        Mockito.lenient().`when`(categoryRepository!!.getCategoriesByBudget(ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildCategories())
        Mockito.lenient().`when`(budgetRepository!!.getValuesByCategories(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues())
        AssertionErrors.assertTrue("The size of returned list is different than two", reportBo!!.byCategory(1).size == 2)
    }

    fun testByCategoryWithoutReturn() {
        Mockito.lenient().`when`(categoryRepository!!.getCategoriesByBudget(ArgumentMatchers.anyInt())).thenReturn(ArrayList())
        Mockito.lenient().`when`(budgetRepository!!.getValuesByCategories(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues())
        AssertionErrors.assertTrue("The returned list isn't empty", reportBo!!.byCategory(1).isEmpty())
    }

    fun testIncomeByPeriodWithReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByBudget(ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildListOfBudgetPeriods())
        Mockito.lenient().`when`(incomeRepository!!.findByDateBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes())
        AssertionErrors.assertTrue("The size of returned list is different than 2", reportBo!!.incomeByPeriod(1).size == 2)
    }

    fun testIncomeByPeriodWithoutReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByBudget(ArgumentMatchers.anyInt())).thenReturn(ArrayList())
        Mockito.lenient().`when`(incomeRepository!!.findByDateBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes())
        AssertionErrors.assertTrue("The size of returned list is different than 2", reportBo!!.incomeByPeriod(1).isEmpty())
    }
}