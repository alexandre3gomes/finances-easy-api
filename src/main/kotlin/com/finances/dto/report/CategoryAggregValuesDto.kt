package com.finances.dto.report

import com.finances.entity.Category


data class CategoryAggregValuesDto(
        val category: Category? = null,
        val periodValue: List<PeriodValueDto>? = null
)