package com.finances.integration.bo

import com.finances.bo.ExpenseBo
import com.finances.dto.ExpenseFilterDTO
import com.finances.entity.Category
import com.finances.entity.Expense
import com.finances.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import java.math.BigDecimal
import java.time.LocalDateTime

@SqlGroup(
    Sql("/db/categories.sql", "/db/users.sql", "/db/expenses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/expenses_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class ExpenseBoTests {

    @Autowired
    lateinit var expenseBo: ExpenseBo

    @Test
    fun testCreateExpense() {
        val expense = expenseBo.create(
            Expense(
                buildCategory(),
                buildUser(),
                "Expense created",
                BigDecimal.TEN,
                LocalDateTime.now(),
                "Expense"
            )
        )
        assertThat(expense).isNotNull
        assertThat(expense.id).isEqualTo(3)
    }

    @Test
    fun testGetExpense() {
        val expense = expenseBo.get(1)
        assertThat(expense).isNotNull
        assertThat(expense.name).isEqualTo("Expense Test")
        assertThat(expense.expireAt).isBefore(LocalDateTime.now())
        assertThat(expense.value).isEqualTo(BigDecimal.valueOf(50))
        assertThat(expense.description).isEqualTo("Description Test")
    }

    @Test
    fun testUpdateExpense() {
        val expense = Expense(
            buildCategory(),
            buildUser(),
            "Expense edited",
            BigDecimal.TEN,
            LocalDateTime.now(),
            "Description edited"
        )
        expense.id = 2
        val editedExpense = expenseBo.update(expense)
        assertThat(editedExpense).isNotNull
        assertThat(editedExpense.name).isEqualTo("Expense edited")
        assertThat(editedExpense.description).isEqualTo("Description edited")
    }

    @Test
    fun testDeleteExpense() {
        expenseBo.delete(2)
        assertThrows<NoSuchElementException> {
            expenseBo.get(2)
        }
    }

    @Test
    fun testListExpenses() {
        val expenses = expenseBo.list(ExpenseFilterDTO(), buildPageRequest())
        assertThat(expenses).isNotNull
        assertThat(expenses.totalElements).isEqualTo(2)
    }

    /*@Test
    fun testListExpenseByName() {
        val filter = ExpenseFilterDTO()
        filter.name = "Expense test 1"
        val expenses = expenseBo.list(filter, buildPageRequest())
        assertThat(expenses.totalElements).isEqualTo(1)
        assertThat(expenses.content[0].name).isEqualTo("Expense test 1")
    }

    @Test
    fun testListExpenseByUser() {
        val filter = ExpenseFilterDTO()
        filter.userId = 2
        val expenses = expenseBo.list(filter, buildPageRequest())
        assertThat(expenses.totalElements).isEqualTo(1)
        assertThat(expenses.content[0].user.id).isEqualTo(2)
    }

    @Test
    fun testListExpenseByCategory() {
        val filter = ExpenseFilterDTO()
        filter.categoryId = 2
        val expenses = expenseBo.list(filter, buildPageRequest())
        assertThat(expenses.totalElements).isEqualTo(1)
        assertThat(expenses.content[0].category.id).isEqualTo(2)
    }

    @Test
    fun testListExpenseByDate() {
        val filter = ExpenseFilterDTO()
        filter.startDate = LocalDateTime.of(2021, 2, 1, 0, 0, 0)
        filter.endDate = LocalDateTime.of(2021, 2, 28, 23, 59, 59)
        val expenses = expenseBo.list(filter, buildPageRequest())
        assertThat(expenses.totalElements).isEqualTo(1)
        assertThat(expenses.content[0].expireAt).isBetween(filter.startDate, filter.endDate)
    }*/

    private fun buildPageRequest(): PageRequest = PageRequest.of(10, 10)

    private fun buildUser(): User {
        val user = User()
        user.id = 1
        return user
    }

    private fun buildCategory(): Category {
        val category = Category()
        category.id = 1
        return category
    }
}
