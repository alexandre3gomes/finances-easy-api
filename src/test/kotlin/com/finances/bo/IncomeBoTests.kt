package com.finances.bo

import com.finances.repository.BudgetRepository
import com.finances.repository.IncomeRepository
import com.finances.util.BuildMockDataUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class IncomeBoTests {
    @Mock
    var incomeRepository: IncomeRepository? = null

    @Mock
    var budgetRepository: BudgetRepository? = null

    @InjectMocks
    var incomeBo: IncomeBo? = null

    fun testGetActualIncomeWithReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods())
        Mockito.lenient().`when`(incomeRepository!!.findByDateBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes())
        Assertions.assertTrue(incomeBo!!.actualIncome.isNotEmpty())
    }

    fun testGetActualIncomeWithoutReturn() {
        Mockito.lenient().`when`(budgetRepository!!.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(Optional.empty())
        Mockito.lenient().`when`(incomeRepository!!.findByDateBetween(ArgumentMatchers.any(LocalDateTime::class.java), ArgumentMatchers.any(LocalDateTime::class.java))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes())
        Assertions.assertFalse(incomeBo!!.actualIncome.isEmpty())
    }
}