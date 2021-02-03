package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.CategoryBo
import com.finances.bo.ExpenseBo
import com.finances.bo.UserBo
import com.finances.controller.ExpenseController
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(ExpenseController::class)
class ExpenseControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var expenseBo: ExpenseBo

    @MockBean
    lateinit var userBo: UserBo

    @MockkBean
    lateinit var categoryBo: CategoryBo

    @Test
    @WithMockUser
    fun testListExpensesOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(EXPENSE_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveExpenseOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$EXPENSE_URL$EXPENSE_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateExpenseOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfExpense()[0])
        mvc.perform(
            MockMvcRequestBuilders.post(EXPENSE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateExpenseOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfExpense()[0])
        mvc.perform(
            MockMvcRequestBuilders.post("$EXPENSE_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteExpenseOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$EXPENSE_URL$EXPENSE_ID").with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val EXPENSE_URL = "/expense"
        const val UPDATE_URL = "/update"
        const val EXPENSE_ID = "/1"
    }
}
