package com.finances.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class IncomeDTO(val id: Int, val user: UserDTO, val name: String, val value: BigDecimal, val date: LocalDateTime, val description: String?)