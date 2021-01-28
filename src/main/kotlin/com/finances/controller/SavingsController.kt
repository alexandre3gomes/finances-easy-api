package com.finances.controller

import com.finances.bo.SavingsBo
import com.finances.dto.SavingsDTO
import com.finances.entity.Savings
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
@RequestMapping("/savings")
class SavingsController(private val savingsBo: SavingsBo) {

    @PostMapping("")
    fun create(@RequestBody sav: Savings): SavingsDTO = savingsBo.create(sav)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = savingsBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): SavingsDTO = savingsBo.get(id)

    @GetMapping("")
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdDate") order: String,
        @RequestParam(defaultValue = "DESC") direction: Sort.Direction
    ): Page<SavingsDTO> {
        return savingsBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @PostMapping("/update")
    fun update(@RequestBody sav: Savings): SavingsDTO = savingsBo.update(sav)
}
