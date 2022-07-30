package com.finances.integration.bo

import com.finances.bo.BpiImporterBo
import com.finances.entity.User
import com.finances.repository.ExpenseRepository
import com.finances.repository.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKException
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.time.LocalDate

@SqlGroup(
    Sql("/db/categories.sql", "/db/users.sql", "/db/expenses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/expenses_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class BpiImporterBoTests {

    @Autowired
    lateinit var importerBo: BpiImporterBo

    @MockkBean
    lateinit var userRepository: UserRepository

    @WithMockUser("admin")
    @Test
    fun testSuccessfulImport() {
        every { userRepository.getUserByUsername(any()) } returns User(1, "admin", "admin")
        val inputStream = this.javaClass.classLoader.getResourceAsStream("importer/movs.xlsx")
        val list = importerBo.import(inputStream)
        list.forEach {
            assertThat(it.id).isNotNull
        }
    }
}