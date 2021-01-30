package com.finances.bo

import com.finances.dto.ExpenseDTO
import com.finances.dto.ExpenseFilterDTO
import com.finances.entity.BudgetPeriods
import com.finances.entity.Expense
import com.finances.mapper.toDTO
import com.finances.repository.BudgetRepository
import com.finances.repository.CategoryRepository
import com.finances.repository.ExpenseRepository
import com.finances.repository.UserRepository
import com.finances.specifications.ExpensesSpec.categoryEquals
import com.finances.specifications.ExpensesSpec.expireAtBetween
import com.finances.specifications.ExpensesSpec.nameEquals
import com.finances.specifications.ExpensesSpec.userEquals
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import kotlin.NoSuchElementException

@Service
class ExpenseBo(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) {

    fun create(expense: Expense): ExpenseDTO {
        return expenseRepository.save(expense).toDTO()
    }

    fun delete(id: Int) {
        expenseRepository.deleteById(id)
    }

    fun get(id: Int): ExpenseDTO {
        return expenseRepository.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(expFilter: ExpenseFilterDTO, pageReq: PageRequest): Page<ExpenseDTO> =
        expenseRepository.findAll(
            Specification.where(
                nameEquals(expFilter.name)
                    .and(
                        categoryEquals(if (expFilter.categoryId > 0) categoryRepository.findById(expFilter.categoryId) else Optional.empty())
                            .and(
                                userEquals(if (expFilter.userId > 0) userRepository.findById(expFilter.userId) else Optional.empty())
                                    .and(expireAtBetween(expFilter.startDate, expFilter.endDate))
                            )
                    )
            ),
            pageReq
        ).map(Expense::toDTO)

    fun update(dev: Expense): ExpenseDTO {
        return expenseRepository.save(dev).toDTO()
    }

    fun actualExpense(): List<ExpenseDTO> {
        val period: BudgetPeriods? = budgetRepository.getPeriodsByDate(LocalDateTime.now())
        if (period != null) {
            return expenseRepository.findByExpireAtBetween(period.startDate, period.endDate).map(Expense::toDTO)
        }
        return emptyList()
    }
}
