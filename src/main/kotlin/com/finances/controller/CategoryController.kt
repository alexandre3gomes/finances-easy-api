package com.finances.controller

import com.finances.bo.CategoryBo
import com.finances.dto.CategoryDTO
import com.finances.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int, @RequestParam(defaultValue = "name") order: String?,
             @RequestParam(defaultValue = "ASC") direction: Sort.Direction): Page<CategoryDTO> {
        return categoryBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody cat: Category): CategoryDTO = categoryBo.update(cat)
}