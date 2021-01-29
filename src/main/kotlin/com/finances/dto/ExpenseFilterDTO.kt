package com.finances.dto

import java.time.LocalDateTime

data class ExpenseFilterDTO(
    var categoryId: Int = 0,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
    var name: String? = null,
    var userId: Int = 0
)
