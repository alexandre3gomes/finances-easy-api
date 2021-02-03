package com.finances.unit.bo

import com.finances.bo.ReportBo
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.repository.ExpenseRepository
import com.finances.repository.IncomeRepository
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(ReportBo::class)
class ReportBoTests {

    @Autowired
    lateinit var reportBo: ReportBo

    @MockkBean
    lateinit var incomeRepository: IncomeRepository

    @MockkBean
    lateinit var budgetRepository: BudgetRepository

    @MockkBean
    lateinit var categoryRepository: CategoryRepository

    @MockBean
    lateinit var expenseRepository: ExpenseRepository

    @Test
    fun testByCategoryWithReturn() {
        every { categoryRepository.getCategoriesByBudget(any()) } returns BuildMockDataUtil.buildListOfCategories()
        every { budgetRepository.getValuesByCategories(any(), any()) } returns BuildMockDataUtil.buildPeriodValues()
        assertThat(reportBo.byCategory(1).size == 2).isTrue
    }

    @Test
    fun testByCategoryWithoutReturn() {
        every { categoryRepository.getCategoriesByBudget(any()) } returns emptyList()
        every { budgetRepository.getValuesByCategories(any(), any()) } returns BuildMockDataUtil.buildPeriodValues()
        assertThat(reportBo.byCategory(1).isEmpty()).isTrue
    }

    @Test
    fun testIncomeByPeriodWithReturn() {
        every { budgetRepository.getPeriodsByBudget(any()) } returns BuildMockDataUtil.buildListOfBudgetPeriods()
        every { incomeRepository.findByDateBetween(any(), any()) } returns BuildMockDataUtil.buildListOfIncomes()
        assertThat(reportBo.incomeByPeriod(1).size == 2).isTrue
    }

    @Test
    fun testIncomeByPeriodWithoutReturn() {
        every { budgetRepository.getPeriodsByBudget(any()) } returns emptyList()
        every { incomeRepository.findByDateBetween(any(), any()) } returns BuildMockDataUtil.buildListOfIncomes()
        assertThat(reportBo.incomeByPeriod(1).isEmpty()).isTrue
    }
}
