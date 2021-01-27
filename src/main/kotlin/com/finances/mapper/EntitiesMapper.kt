package com.finances.mapper

import com.finances.dto.*
import com.finances.entity.*

fun User.toDTO() = UserDTO(id, name, username)

fun Category.toDTO() = CategoryDTO(id, name, savings)

fun Income.toDTO() = IncomeDTO(id, user.toDTO(), name, value, date, description)

fun Expense.toDTO() = ExpenseDTO(id, category!!.toDTO(), user.toDTO(), name, value, expireAt, description)

fun Savings.toDTO() = SavingsDTO(id, description, value, user.toDTO(), createdDate)

fun BudgetCategories.toDTO() = BudgetCategoriesDTO(category.toDTO(), value)

fun Budget.toDTO() = BudgetDTO(
        id,
        startDate,
        endDate,
        breakperiod,
        user.toDTO(),
        categories.map(BudgetCategories::toDTO)
)

fun UserDTO.toEntity(): User {
    val user = User(name, username)
    user.id = id
    return user
}
