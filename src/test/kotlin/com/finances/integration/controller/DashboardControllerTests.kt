package com.finances.integration.controller

import com.finances.bo.BudgetBo
import com.finances.bo.ExpenseBo
import com.finances.bo.IncomeBo
import com.finances.bo.SavingsBo
import com.finances.controller.DashboardController
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
@WebMvcTest(DashboardController::class)
class DashboardControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockkBean
    lateinit var expenseBo: ExpenseBo

    @MockkBean
    lateinit var incomeBo: IncomeBo

    @MockkBean
    lateinit var budgetBo: BudgetBo

    @MockkBean
    lateinit var savingsBo: SavingsBo

    @Test
    @WithMockUser
    fun testActualExpense() {
        mvc.perform(
            MockMvcRequestBuilders.get("$DASHBOARD_URL$ACTUAL_EXPENSE_URL")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testActualIncome() {
        mvc.perform(
            MockMvcRequestBuilders.get("$DASHBOARD_URL$ACTUAL_INCOME_URL")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testActualBalance() {
        mvc.perform(
            MockMvcRequestBuilders.get("$DASHBOARD_URL$ACTUAL_BALANCE_URL")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testTotalSavings() {
        mvc.perform(
            MockMvcRequestBuilders.get("$DASHBOARD_URL$ACTUAL_SAVINGS_URL")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val DASHBOARD_URL = "/dashboard"
        const val ACTUAL_EXPENSE_URL = "/actualExpense"
        const val ACTUAL_INCOME_URL = "/actualIncome"
        const val ACTUAL_BALANCE_URL = "/actualBalance"
        const val ACTUAL_SAVINGS_URL = "/totalSavings"
    }
}
