package com.finances.bo

import com.finances.dto.ExpenseFilterDTO
import com.finances.repository.BudgetRepository
import com.finances.repository.ExpenseRepository
import com.finances.util.BuildMockDataUtil
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest
import org.springframework.test.util.AssertionErrors
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ExpenseBoTests {
    @Mock
    var expenseRepository: ExpenseRepository? = null

    @Mock
    var budgetRepository: BudgetRepository? = null

    @InjectMocks
    var expenseBo: ExpenseBo? = null
    fun testListWithoutFilters() {
        Mockito.lenient().`when`(expenseRepository!!.findAll(ArgumentMatchers.any(PageRequest::class.java))).thenReturn(BuildMockDataUtil.buildPageOfExpenses())
        AssertionErrors.assertTrue("The size of returned list is different than ten", expenseBo!!.list(ExpenseFilterDTO(), PageRequest.of(1, 10)).totalElements == 10L)
    }

    fun testListWithNameFilter() {
        Mockito.lenient().`when`(expenseRepository!!.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense())
        AssertionErrors.assertTrue("The size of returned list is different than ten", expenseBo!!.list(BuildMockDataUtil.buildExpenseFilterDTO(), PageRequest.of(1, 10)).totalElements == 10L)
    }

    fun testGetActualExpenseWithReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods())
        Mockito.lenient().`when`(expenseRepository!!.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense())
        AssertionErrors.assertTrue("The size of returned list is different than ten", expenseBo!!.actualExpense.isNotEmpty())
    }

    fun testGetActualExpenseWithoutReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(Optional.empty())
        Mockito.lenient().`when`(expenseRepository!!.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense())
        AssertionErrors.assertTrue("The returned list isn't empty", expenseBo!!.actualExpense.isEmpty())
    }
}
