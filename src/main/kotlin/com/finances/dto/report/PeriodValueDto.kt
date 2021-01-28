package com.finances.dto.report

import java.math.BigDecimal
import java.time.LocalDateTime

data class PeriodValueDto(
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val plannedValue: BigDecimal? = null,
    val actualValue: BigDecimal? = null
)
