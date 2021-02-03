package com.finances.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.finances.bo.CategoryBo
import com.finances.controller.CategoryController
import com.finances.util.BuildMockDataUtil
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(CategoryController::class)
class CategoryControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockkBean
    lateinit var categoryBo: CategoryBo

    @Test
    @WithMockUser
    fun testListCategoriesOk() {
        mvc.perform(
            MockMvcRequestBuilders.get(CATEGORY_URL)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testRetrieveCategoryOk() {
        mvc.perform(
            MockMvcRequestBuilders.get("$CATEGORY_URL$CATEGORY_ID")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testCreateCategoryOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfCategories()[0])
        mvc.perform(
            MockMvcRequestBuilders.post(CATEGORY_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testUpdateCategoryOk() {
        val json = mapper.writeValueAsString(BuildMockDataUtil.buildListOfCategories()[0])
        mvc.perform(
            MockMvcRequestBuilders.post("$CATEGORY_URL$UPDATE_URL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun testDeleteCategoryOk() {
        mvc.perform(
            MockMvcRequestBuilders.delete("$CATEGORY_URL$CATEGORY_ID").with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    companion object {
        const val CATEGORY_URL = "/category"
        const val CATEGORY_ID = "/1"
        const val UPDATE_URL = "/update"
    }
}
