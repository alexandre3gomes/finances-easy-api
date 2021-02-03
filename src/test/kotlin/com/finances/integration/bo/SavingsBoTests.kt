package com.finances.integration.bo

import com.finances.bo.SavingsBo
import com.finances.entity.Savings
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
    Sql("/db/users.sql", "/db/savings.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/savings_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class SavingsBoTests {

    @Autowired
    lateinit var savingsBo: SavingsBo

    @Test
    fun testCreateSavings() {
        val savings = savingsBo.create(
            Savings(
                buildUser(),
                "Savings created",
                BigDecimal.TEN,
                LocalDateTime.now()
            )
        )
        assertThat(savings).isNotNull
        assertThat(savings.id).isEqualTo(3)
    }

    @Test
    fun testGetSavings() {
        val savings = savingsBo.get(1)
        assertThat(savings).isNotNull
        assertThat(savings.description).isEqualTo("Savings Test")
        assertThat(savings.createdDate).isBefore(LocalDateTime.now())
        assertThat(savings.value).isEqualTo(BigDecimal.valueOf(300))
    }

    @Test
    fun testUpdateSavings() {
        val savings = Savings(
            buildUser(),
            "Savings edited",
            BigDecimal.TEN,
            LocalDateTime.now()
        )
        savings.id = 2
        val editedSavings = savingsBo.update(savings)
        assertThat(editedSavings).isNotNull
        assertThat(editedSavings.description).isEqualTo("Savings edited")
        assertThat(editedSavings.value).isEqualTo(BigDecimal.TEN)
    }

    @Test
    fun testDeleteSavings() {
        savingsBo.delete(2)
        assertThrows<NoSuchElementException> {
            savingsBo.get(2)
        }
    }

    @Test
    fun testListSavingss() {
        val savings = savingsBo.list(buildPageRequest())
        assertThat(savings).isNotNull
        assertThat(savings.totalElements).isEqualTo(2)
    }

    private fun buildPageRequest(): PageRequest = PageRequest.of(10, 10)

    private fun buildUser(): User {
        val user = User()
        user.id = 1
        return user
    }
}
