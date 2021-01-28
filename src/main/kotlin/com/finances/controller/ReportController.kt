package com.finances.controller

import com.finances.bo.ReportBo
import com.finances.dto.report.CategoryAggregValuesDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/report")
class ReportController(private val reportBo: ReportBo) {

    @GetMapping("/byCategory/{budgetId}")
    fun byCategory(@PathVariable("budgetId") budgetId: Int): List<CategoryAggregValuesDto> = reportBo.byCategory(budgetId)

    @GetMapping("/byPeriod/{budgetId}")
    fun incomeByPeriod(@PathVariable("budgetId") budgetId: Int): List<BigDecimal> = reportBo.incomeByPeriod(budgetId)
}
