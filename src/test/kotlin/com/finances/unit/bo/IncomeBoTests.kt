package com.finances.unit.bo

import com.finances.bo.IncomeBo
import com.finances.repository.BudgetRepository
import com.finances.repository.IncomeRepository
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(IncomeBo::class)
class IncomeBoTests {

    @Autowired
    lateinit var incomeBo: IncomeBo

    @MockkBean
    lateinit var incomeRepository: IncomeRepository

    @MockkBean
    lateinit var budgetRepository: BudgetRepository

    @Test
    fun testGetActualIncomeWithReturn() {
        every { budgetRepository.getPeriodsByDate(any()) } returns BuildMockDataUtil.buildBudgetPeriods()
        every { incomeRepository.findByDateBetween(any(), any()) } returns BuildMockDataUtil.buildListOfIncomes()
        assertThat(incomeBo.actualIncome().isNotEmpty()).isTrue
    }

    @Test
    fun testGetActualIncomeWithoutReturn() {
        every { budgetRepository.getPeriodsByDate(any()) } returns null
        every { incomeRepository.findByDateBetween(any(), any()) } returns BuildMockDataUtil.buildListOfIncomes()
        assertThat(incomeBo.actualIncome().isEmpty()).isTrue
    }
}
