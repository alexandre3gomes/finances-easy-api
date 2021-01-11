package com.finances.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data  class SavingsDTO (val id: Int, val description: String, val value: BigDecimal, val user: UserDTO, val createdDate: LocalDateTime)