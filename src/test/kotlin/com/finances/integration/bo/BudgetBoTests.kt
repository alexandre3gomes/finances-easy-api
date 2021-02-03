package com.finances.integration.bo

import com.finances.bo.BudgetBo
import com.finances.entity.Budget
import com.finances.entity.User
import com.finances.enums.BreakpointEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import java.time.LocalDateTime
import javax.transaction.Transactional

@SqlGroup(
    Sql("/db/users.sql", "/db/categories.sql", "/db/budgets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/budgets_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class BudgetBoTests {

    @Autowired
    lateinit var budgetBo: BudgetBo

    @Test
    fun testCreateBudget() {
        val budget = budgetBo.create(
            Budget(
                buildUser(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                BreakpointEnum.MONTHLY.id
            )
        )
        assertThat(budget).isNotNull
        assertThat(budget.id).isEqualTo(4)
    }

    @Transactional
    @Test
    fun testGetBudget() {
        val budget = budgetBo.get(1)
        assertThat(budget).isNotNull
        assertThat(budget.startDate).isBefore(LocalDateTime.now())
        assertThat(budget.endDate).isBefore(LocalDateTime.now())
    }

    @Test
    fun testUpdateBudget() {
        val budget = Budget(
            buildUser(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            BreakpointEnum.MONTHLY.id
        )
        budget.id = 2
        val editedBudget = budgetBo.update(budget)
        assertThat(editedBudget).isNotNull
        assertThat(editedBudget.id).isNotEqualTo(2)
    }

    @Test
    fun testDeleteBudget() {
        budgetBo.delete(2)
        assertThrows<NoSuchElementException> {
            budgetBo.get(2)
        }
    }

    @Test
    fun testListBudgets() {
        val budget = budgetBo.list(buildPageRequest())
        assertThat(budget).isNotNull
        assertThat(budget.totalElements).isEqualTo(2)
    }

    private fun buildPageRequest(): PageRequest = PageRequest.of(10, 10)

    private fun buildUser(): User {
        val user = User()
        user.id = 1
        return user
    }
}
