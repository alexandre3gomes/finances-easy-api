package com.finances.dto.report

import java.math.BigDecimal
import java.time.LocalDateTime

interface PeriodValueDto {
    fun getStartDate(): LocalDateTime
    fun getEndDate(): LocalDateTime
    fun getPlannedValue(): BigDecimal
    fun getActualValue(): BigDecimal
}
