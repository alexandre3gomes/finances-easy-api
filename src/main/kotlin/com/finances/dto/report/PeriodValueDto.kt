package com.finances.dto.report

import java.math.BigDecimal
import java.time.LocalDateTime

interface PeriodValueDto {
    val startDate: LocalDateTime
    val endDate: LocalDateTime
    val plannedValue: BigDecimal
    val actualValue: BigDecimal
}
