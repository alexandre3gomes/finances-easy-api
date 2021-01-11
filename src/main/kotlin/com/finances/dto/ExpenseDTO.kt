package com.finances.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExpenseDTO(val id: Int,
                      val category: CategoryDTO,
                      val user: UserDTO,
                      val name: String,
                      val value: BigDecimal,
                      val expireAt: LocalDateTime,
                      val description: String?
)