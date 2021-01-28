package com.finances.dto

import java.time.LocalDateTime

data class BudgetDTO(
    val id: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val breakperiod: Int,
    val user: UserDTO,
    val categories: List<BudgetCategoriesDTO>
)
