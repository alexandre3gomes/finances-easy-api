package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.BudgetBo
import com.finances.controller.BudgetController
import com.finances.enums.BreakpointEnum
import com.finances.mapper.toDTO
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(BudgetController::class)
class BudgetControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var budgetBo: BudgetBo

    @BeforeEach
    fun setup() {
        every { budgetBo.create(any()) } returns BuildMockDataUtil.buildListOfBudgets(BreakpointEnum.MONTHLY)[0].toDTO()
    }

    @Test
    @WithMockUser
    fun testListBudgetsOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(BUDGET_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveBudgetOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$BUDGET_URL$BUDGET_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateBudgetOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfBudgets(BreakpointEnum.MONTHLY)[0])
        mvc.perform(
            MockMvcRequestBuilders.post(BUDGET_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateBudgetOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfBudgets(BreakpointEnum.MONTHLY)[0])
        mvc.perform(
            MockMvcRequestBuilders.post("$BUDGET_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteBudgetOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$BUDGET_URL$BUDGET_ID").with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val BUDGET_URL = "/budget"
        const val UPDATE_URL = "/update"
        const val BUDGET_ID = "/1"
    }
}
