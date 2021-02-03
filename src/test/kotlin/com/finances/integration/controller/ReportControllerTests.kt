package com.finances.integration.controller

import com.finances.bo.ReportBo
import com.finances.controller.ReportController
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(ReportController::class)
class ReportControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockkBean
    lateinit var reportBo: ReportBo

    @Test
    @WithMockUser
    fun testByCategory() {
        mvc.perform(
            MockMvcRequestBuilders.get("$REPORT_URL$BY_CATEGORY_URL$BUDGET_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testeIncomeByPeriod() {
        mvc.perform(
            MockMvcRequestBuilders.get("$REPORT_URL$INCOME_BY_PERIOD_URL$BUDGET_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val REPORT_URL = "/report"
        const val BY_CATEGORY_URL = "/byCategory"
        const val INCOME_BY_PERIOD_URL = "/byPeriod"
        const val BUDGET_ID = "/1"
    }
}
