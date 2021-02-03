package com.finances.integration.bo

import com.finances.bo.CategoryBo
import com.finances.entity.Category
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@SqlGroup(
    Sql("/db/categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/categories_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class CategoryBoTests {

    @Autowired
    lateinit var categoryBo: CategoryBo

    @Test
    fun testCreateCategory() {
        val category = categoryBo.create(Category("Category test programatic", false))
        assertThat(category).isNotNull
        assertThat(category.id).isEqualTo(3)
    }

    @Test
    fun testGetCategory() {
        val category = categoryBo.get(1)
        assertThat(category).isNotNull
        assertThat(category.name).isEqualTo("Category Test")
        assertThat(category.savings).isEqualTo(false)
    }

    @Test
    fun testUpdateCategory() {
        val category = Category("Category edited", false)
        category.id = 2
        val editedCategory = categoryBo.update(category)
        assertThat(editedCategory).isNotNull
        assertThat(editedCategory.name).isEqualTo("Category edited")
        assertThat(editedCategory.savings).isEqualTo(false)
    }

    @Test
    fun testDeleteCategory() {
        categoryBo.delete(2)
        assertThrows<NoSuchElementException> {
            categoryBo.get(2)
        }
    }

    @Test
    fun testListCategories() {
        val categories = categoryBo.list(PageRequest.of(10, 10))
        assertThat(categories).isNotNull
        assertThat(categories.totalElements).isEqualTo(2)
    }
}
