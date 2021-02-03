package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.IncomeBo
import com.finances.controller.IncomeController
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
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
@WebMvcTest(IncomeController::class)
class IncomeControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var incomeBo: IncomeBo

    @Test
    @WithMockUser
    fun testListIncomesOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(INCOME_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveIncomeOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$INCOME_URL$INCOME_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateIncomeOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfIncomes()[0])
        mvc.perform(
            MockMvcRequestBuilders.post(INCOME_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateincomeOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfIncomes()[0])
        mvc.perform(
            MockMvcRequestBuilders.post("$INCOME_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteincomeOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$INCOME_URL$INCOME_ID").with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val INCOME_URL = "/income"
        const val UPDATE_URL = "/update"
        const val INCOME_ID = "/1"
    }
}
