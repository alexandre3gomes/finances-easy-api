package com.finances.unit.bo

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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.time.LocalDate

@SpringJUnitConfig(BpiImporterBo::class)
class BpiImporterBoTests {

    @Autowired
    lateinit var importerBo: BpiImporterBo

    @MockkBean
    lateinit var expenseRepository: ExpenseRepository

    @MockkBean
    lateinit var userRepository: UserRepository

    @WithMockUser("admin")
    @Test
    fun testSuccessfulConvertToExpenses() {
        every { userRepository.getUserByUsername("admin") } returns User(1, "test", "test")
        val inputStream = this.javaClass.classLoader.getResourceAsStream("importer/movs.xlsx")
        val list = importerBo.convertToExpenses(inputStream)
        assertThat(list.size).isEqualTo(14)
        assertThat(list.first().name).isEqualTo("22/07 COMPRA ELEC 3928577/99 PIZZA HUT SETUBAL   2910-435 SE")
        assertThat(list.first().value).isEqualTo(BigDecimal.valueOf(21.20))
        assertThat(list.first().expireAt.toLocalDate()).isEqualTo(LocalDate.of(2022, 7, 22))
        assertThat(list.first().user.name).isEqualTo("test")
        assertThat(list.last().name).isEqualTo("14/07 COMPRA ELEC 3928577/91 LIDL AGRADECE       1070-060 ES")
        assertThat(list.last().value).isEqualTo(BigDecimal.valueOf(0.99))
        assertThat(list.last().expireAt.toLocalDate()).isEqualTo(LocalDate.of(2022, 7, 14))
        assertThat(list[6].expireAt.toLocalDate()).isEqualTo(LocalDate.of(2022, 7, 17))
        assertThat(list[7].expireAt.toLocalDate()).isEqualTo(LocalDate.of(2022, 7, 17))
    }

    @WithMockUser("admin")
    @Test
    fun testWrongUserConvertToExpenses() {
        every { userRepository.getUserByUsername("wrong") } returns User(1, "test", "test")
        val inputStream = this.javaClass.classLoader.getResourceAsStream("importer/movs.xlsx")
        val ex = assertThrows<MockKException> {
            importerBo.convertToExpenses(inputStream)
        }
        assertThat(ex.message).contains("no answer found for:")
    }

    @Test
    fun testWithoutUserConvertToExpenses() {
        every { userRepository.getUserByUsername("admin") } returns User(1, "test", "test")
        val inputStream = this.javaClass.classLoader.getResourceAsStream("importer/movs.xlsx")
        assertThrows<UserPrincipalNotFoundException> {
            importerBo.convertToExpenses(inputStream)
        }
    }

    @Test
    fun testConvertToDateSuccessful() {
        val date = importerBo.convertToDate("05-02-2022")
        assertThat(date).isNotNull
        assertThat(date).isInstanceOf(LocalDate::class.java)
        assertThat(date).isEqualTo(LocalDate.of(2022, 2, 5))
    }

    @Test
    fun testConvertToDateReturnsNull() {
        assertThat(importerBo.convertToDate("2022-02-05")).isNull()
        assertThat(importerBo.convertToDate("05/12/2022")).isNull()
        assertThat(importerBo.convertToDate("2022/02/05")).isNull()
        assertThat(importerBo.convertToDate("InvalidDate")).isNull()
    }
}
