package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.SavingsBo
import com.finances.controller.SavingsController
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
@WebMvcTest(SavingsController::class)
class SavingsControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var savingsBo: SavingsBo

    @Test
    @WithMockUser
    fun testListSavingsOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(SAVINGS_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveSavingsOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$SAVINGS_URL$SAVINGS_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateSavingsOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfSavings()[0])
        mvc.perform(
            MockMvcRequestBuilders.post(SAVINGS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateSavingsOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfSavings()[0])
        mvc.perform(
            MockMvcRequestBuilders.post("$SAVINGS_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteSavingsOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$SAVINGS_URL$SAVINGS_ID").with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val SAVINGS_URL = "/savings"
        const val UPDATE_URL = "/update"
        const val SAVINGS_ID = "/1"
    }
}
