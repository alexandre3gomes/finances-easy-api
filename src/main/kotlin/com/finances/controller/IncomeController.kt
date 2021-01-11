package com.finances.controller

import com.finances.bo.IncomeBo
import com.finances.dto.IncomeDTO
import com.finances.entity.Income
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/income")
class IncomeController(private val incomeBo: IncomeBo) {

    @PostMapping("")
    fun create(@RequestBody income: Income): IncomeDTO = incomeBo.create(income)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = incomeBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): IncomeDTO = incomeBo.get(id)

    @GetMapping("")
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int, @RequestParam(defaultValue = "date") order: String?,
             @RequestParam(defaultValue = "DESC") direction: Sort.Direction): Page<IncomeDTO>? {
        return incomeBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody income: Income): IncomeDTO = incomeBo.update(income)
}