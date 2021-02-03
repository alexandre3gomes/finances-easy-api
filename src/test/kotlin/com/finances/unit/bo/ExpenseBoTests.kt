package com.finances.unit.bo

import com.finances.bo.ExpenseBo
import com.finances.dto.ExpenseFilterDTO
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.repository.ExpenseRepository
import com.finances.repository.UserRepository
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.util.Optional

@SpringJUnitConfig(ExpenseBo::class)
class ExpenseBoTests {

    @Autowired
    lateinit var expenseBo: ExpenseBo

    @MockkBean
    lateinit var expenseRepository: ExpenseRepository

    @MockkBean
    lateinit var budgetRepository: BudgetRepository

    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var catRepository: CategoryRepository

    @Test
    fun testListWithoutFilters() {
        every { expenseRepository.findAll(any(), ofType(Pageable::class)) } returns BuildMockDataUtil.buildPageOfExpenses()
        assertThat(
            expenseBo.list(ExpenseFilterDTO(), PageRequest.of(1, 10)).totalElements == 10L
        ).isTrue
    }

    @Test
    fun testListWithNameFilter() {
        every { expenseRepository.findAll(any(), ofType(Pageable::class)) } returns BuildMockDataUtil.buildPageOfExpenses()
        every { catRepository.findById(any()) } returns Optional.of(BuildMockDataUtil.buildListOfCategories()[0])
        every { userRepository.findById(any()) } returns Optional.of(BuildMockDataUtil.buildUser())
        assertThat(
            expenseBo.list(BuildMockDataUtil.buildExpenseFilterDTO(), PageRequest.of(1, 10)).totalElements == 10L
        ).isTrue
    }

    @Test
    fun testGetActualExpenseWithReturn() {
        every { budgetRepository.getPeriodsByDate(any()) } returns BuildMockDataUtil.buildBudgetPeriods()
        every { expenseRepository.findByExpireAtBetween(any(), any()) } returns BuildMockDataUtil.buildListOfExpense()
        assertThat(expenseBo.actualExpense().isNotEmpty()).isTrue
    }

    @Test
    fun testGetActualExpenseWithoutReturn() {
        every { budgetRepository.getPeriodsByDate(any()) } returns null
        every { expenseRepository.findByExpireAtBetween(any(), any()) } returns BuildMockDataUtil.buildListOfExpense()
        assertThat(expenseBo.actualExpense().isEmpty()).isTrue
    }
}
