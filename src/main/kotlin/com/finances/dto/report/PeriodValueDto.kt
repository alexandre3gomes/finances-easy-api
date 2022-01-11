package com.finances.dto.report

import java.math.BigDecimal
import java.time.LocalDateTime

interface PeriodValueDto {
    fun getStartDate(): LocalDateTime
    fun getEndDate(): LocalDateTime
    fun getPlannedValue(): BigDecimal
    fun getActualValue(): BigDecimal
    fun setStartDate(startDate: LocalDateTime): LocalDateTime
    fun setEndDate(endDate: LocalDateTime): LocalDateTime
    fun setPlannedValue(planned: BigDecimal): BigDecimal
    fun setActualValue(actual: BigDecimal): BigDecimal
}
