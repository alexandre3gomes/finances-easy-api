package com.finances.integration.bo

import com.finances.bo.ReportBo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import java.math.BigDecimal

@SqlGroup(
    Sql("/db/users.sql", "/db/categories.sql", "/db/incomes.sql", "/db/expenses.sql", "/db/budgets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/db_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class ReportBoTests {

    @Autowired
    lateinit var reportBo: ReportBo

    @Test
    fun testByCategory() {
        val aggregatedCat = reportBo.byCategory(1)
        assertThat(aggregatedCat).isNotNull
        assertThat(aggregatedCat.size).isEqualTo(2)
        assertThat(aggregatedCat[0].category).isNotNull
        assertThat(aggregatedCat[0].periodValue).isNotNull
        assertThat(aggregatedCat[0].periodValue!!.size).isEqualTo(12)
        assertThat(aggregatedCat[1].category).isNotNull
        assertThat(aggregatedCat[1].periodValue).isNotNull
        assertThat(aggregatedCat[1].periodValue!!.size).isEqualTo(12)
    }

    @Test
    fun testIncomeByPeriod() {
        val incomes = reportBo.incomeByPeriod(1)
        assertThat(incomes).isNotNull
        assertThat(incomes.size).isEqualTo(12)
        assertThat(incomes.map(BigDecimal::toDouble).sum()).isEqualTo(300.0)
    }
}
