package com.finances.controller

import com.finances.bo.CategoryBo
import com.finances.bo.ExpenseBo
import com.finances.bo.UserBo
import com.finances.dto.ExpenseDTO
import com.finances.dto.ExpenseFilterDTO
import com.finances.entity.Expense
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/expense")
class ExpenseController(private val expenseBo: ExpenseBo, private val userBo: UserBo, private val categoryBo: CategoryBo) {

    @PostMapping("")
    fun create(@RequestBody expense: Expense): ExpenseDTO = expenseBo.create(expense)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = expenseBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): ExpenseDTO = expenseBo.get(id)

    @GetMapping("")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "expireAt") order: String?,
        @RequestParam(defaultValue = "DESC") direction: Sort.Direction,
        @RequestParam(name = "category", required = false, defaultValue = 0.toString()) category: Int,
        @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime?,
        @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime?,
        @RequestParam(name = "name", required = false) name: String?,
        @RequestParam(name = "user", required = false, defaultValue = 0.toString()) user: Int
    ): Page<ExpenseDTO> {
        val expFilter = ExpenseFilterDTO(category, startDate, endDate, name, user)
        return expenseBo.list(expFilter, PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody dev: Expense): ExpenseDTO = expenseBo.update(dev)
}
