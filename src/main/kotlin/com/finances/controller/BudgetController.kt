package com.finances.controller

import com.finances.bo.BudgetBo
import com.finances.dto.BudgetDTO
import com.finances.entity.Budget
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
@RequestMapping("/budget")
class BudgetController(private val budgetBo: BudgetBo) {

    @PostMapping("")
    fun create(@RequestBody budget: Budget): BudgetDTO = budgetBo.create(budget)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = budgetBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): BudgetDTO = budgetBo.get(id)

    @GetMapping("")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "startDate") order: String?,
        @RequestParam(defaultValue = "DESC") direction: Sort.Direction
    ): Page<BudgetDTO> {
        return budgetBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody budget: Budget): BudgetDTO = budgetBo.update(budget)
}
