package com.finances.controller

import com.finances.bo.CategoryBo
import com.finances.dto.CategoryDTO
import com.finances.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryBo: CategoryBo) {

    @PostMapping("")
    fun create(@RequestBody cat: Category): CategoryDTO = categoryBo.create(cat)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = categoryBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): CategoryDTO = categoryBo.get(id)

    @GetMapping("")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "name") order: String?,
        @RequestParam(defaultValue = "ASC") direction: Sort.Direction
    ): Page<CategoryDTO> {
        return categoryBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody cat: Category): CategoryDTO = categoryBo.update(cat)
}
