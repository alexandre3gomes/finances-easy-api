package com.finances.controller

import com.finances.bo.BudgetBo
import com.finances.dto.BudgetDTO
import com.finances.entity.Budget
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/budget")
class BudgetController(private val budgetBo: BudgetBo) {

    @PostMapping("")
    fun create(@RequestBody budget: Budget): BudgetDTO = budgetBo.create(budget)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = budgetBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): BudgetDTO = budgetBo.get(id)

    @GetMapping("")
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int,
             @RequestParam(defaultValue = "startDate") order: String?,
             @RequestParam(defaultValue = "DESC") direction: Sort.Direction): Page<BudgetDTO> {
        return budgetBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody budget: Budget): BudgetDTO = budgetBo.update(budget)
}