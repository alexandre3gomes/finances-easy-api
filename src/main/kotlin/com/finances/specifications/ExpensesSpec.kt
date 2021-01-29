package com.finances.specifications

import com.finances.entity.Category
import com.finances.entity.Expense
import com.finances.entity.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import java.util.Optional
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Root

object ExpensesSpec {

    @JvmStatic
    fun nameEquals(name: String?): Specification<Expense> {
        return Specification { expense: Root<Expense>, _, builder: CriteriaBuilder ->
            if (name.isNullOrBlank()) builder.conjunction()
            else builder.like(builder.lower(expense.get("name")), "%${name.toLowerCase()}%")
        }
    }

    @JvmStatic
    fun expireAtBetween(startDate: LocalDateTime?, endDate: LocalDateTime?): Specification<Expense> {
        return Specification { expense: Root<Expense>, _, builder: CriteriaBuilder ->
            if (startDate == null || endDate == null) builder.conjunction() else builder.between(
                expense.get("expireAt"),
                startDate,
                endDate
            )
        }
    }

    @JvmStatic
    fun categoryEquals(category: Optional<Category>): Specification<Expense> {
        return Specification { expense: Root<Expense>, _, builder: CriteriaBuilder ->
            if (category.isEmpty) builder.conjunction()
            else builder.equal(expense.get<Any>("category"), category.get())
        }
    }

    @JvmStatic
    fun userEquals(user: Optional<User>): Specification<Expense> {
        return Specification { expense: Root<Expense>, _, builder: CriteriaBuilder ->
            if (user.isEmpty) builder.conjunction()
            else builder.equal(expense.get<Any>("user"), user.get())
        }
    }
}
