package com.finances.integration.bo

import com.finances.bo.IncomeBo
import com.finances.entity.Income
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
    Sql("/db/users.sql", "/db/incomes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/incomes_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class IncomeBoTests {

    @Autowired
    lateinit var incomeBo: IncomeBo

    @Test
    fun testCreateIncome() {
        val income = incomeBo.create(Income(buildUser(), "Income created", BigDecimal.TEN, LocalDateTime.now(), "Description created 3"))
        assertThat(income).isNotNull
        assertThat(income.id).isEqualTo(3)
    }

    @Test
    fun testGetIncome() {
        val income = incomeBo.get(1)
        assertThat(income).isNotNull
        assertThat(income.name).isEqualTo("Income Test")
        assertThat(income.date).isBefore(LocalDateTime.now())
        assertThat(income.value).isEqualTo(BigDecimal.valueOf(100))
        assertThat(income.description).isEqualTo("Description Test")
    }

    @Test
    fun testUpdateIncome() {
        val income = Income(buildUser(), "Income edited", BigDecimal.TEN, LocalDateTime.now(), "Description edited")
        income.id = 2
        val editedIncome = incomeBo.update(income)
        assertThat(editedIncome).isNotNull
        assertThat(editedIncome.name).isEqualTo("Income edited")
        assertThat(editedIncome.description).isEqualTo("Description edited")
    }

    @Test
    fun testDeleteIncome() {
        incomeBo.delete(2)
        assertThrows<NoSuchElementException> {
            incomeBo.get(2)
        }
    }

    @Test
    fun testListIncomes() {
        val incomes = incomeBo.list(PageRequest.of(10, 10))
        assertThat(incomes).isNotNull
        assertThat(incomes.totalElements).isEqualTo(2)
    }

    private fun buildUser(): User {
        val user = User()
        user.id = 1
        return user
    }
}
