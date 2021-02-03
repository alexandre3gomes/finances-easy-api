package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.UserBo
import com.finances.controller.UserController
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
@WebMvcTest(UserController::class)
class UserControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var userBo: UserBo

    @Test
    @WithMockUser
    fun testListUsersOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(USER_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveUserOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$USER_URL$USER_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateUserOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildUser())
        mvc.perform(
            MockMvcRequestBuilders.post(USER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateUserOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildUser())
        mvc.perform(
            MockMvcRequestBuilders.post("$USER_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteUserOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$USER_URL$USER_ID").with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val USER_URL = "/user"
        const val UPDATE_URL = "/update"
        const val USER_ID = "/1"
    }
}
