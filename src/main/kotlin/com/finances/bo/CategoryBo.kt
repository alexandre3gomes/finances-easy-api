package com.finances.bo

import com.finances.dto.CategoryDTO
import com.finances.entity.Category
import com.finances.mapper.toDTO
import com.finances.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryBo(private val categoryRep: CategoryRepository) {

    fun create(budget: Category): CategoryDTO {
        return categoryRep.save(budget).toDTO()
    }

    fun delete(id: Int) {
        categoryRep.deleteById(id)
    }

    fun get(id: Int): CategoryDTO {
        return categoryRep.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(pageReq: PageRequest): Page<CategoryDTO> {
        return categoryRep.findAll(pageReq).map(Category::toDTO)
    }

    fun update(cat: Category): CategoryDTO {
        return categoryRep.save(cat).toDTO()
    }
}