package com.finances.util

import com.finances.dto.ExpenseFilterDTO
import com.finances.dto.report.PeriodValueDto
import com.finances.entity.Budget
import com.finances.entity.BudgetCategories
import com.finances.entity.BudgetPeriods
import com.finances.entity.Category
import com.finances.entity.Expense
import com.finances.entity.Income
import com.finances.entity.User
import com.finances.enums.BreakpointEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

object BuildMockDataUtil {
    fun buildPageOfExpenses(): Page<Expense> {
        val expenses = mutableListOf<Expense>()
        for (x in 0..9) {
            val category = Category(x + 1, "Name test ${x + 1}", false)
            val user = buildUser()
            expenses.add(Expense(x, category, user, "Expense ${x + 1}", BigDecimal.valueOf(100), LocalDateTime.now(), "Description ${x + 1}"))
        }
        return PageImpl(expenses)
    }

    fun buildPeriodValues(): List<PeriodValueDto> {
        val periodValueDto = PeriodValueDto(
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME),
            BigDecimal.valueOf(100),
            BigDecimal.valueOf(150)
        )
        return listOf(periodValueDto)
    }

    fun buildCategories() = listOf(Category(1, "Test 1", false), Category(1, "Test 2", false))

    fun buildBudgetPeriods(): BudgetPeriods = BudgetPeriods(
        buildBudget(BreakpointEnum.MONTHLY),
        1,
        LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
    )

    fun buildBudget(breakpoint: BreakpointEnum): Budget {
        val budget = Budget(
            buildUser(),
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME),
            breakpoint.id
        )
        budget.id = 1
        budget.categories = buildSetOfBudgetCategories()
        return budget
    }

    fun buildSetOfBudgetCategories(): Set<BudgetCategories> {
        val catBud = BudgetCategories(
            buildCategories()[0],
            Budget(
                buildUser(),
                LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME),
                BreakpointEnum.MONTHLY.id
            )
        )
        catBud.value = BigDecimal.valueOf(100)
        return setOf(catBud)
    }

    fun buildExpenseFilterDTO(): ExpenseFilterDTO {
        val filter = ExpenseFilterDTO()
        filter.name = "Name test 1"
        filter.categoryId = 1
        filter.startDate = LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME)
        filter.endDate = LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        filter.userId = 1
        return filter
    }

    fun buildListOfIncomes(): List<Income> {
        return listOf(Income(1, buildUser(), "Income", BigDecimal.valueOf(100), LocalDateTime.now(), "Income description"))
    }

    fun buildUser() = User("Test user", "test")

    fun buildListOfBudgetPeriods(): List<BudgetPeriods> = listOf(
        BudgetPeriods(
            buildBudget(BreakpointEnum.MONTHLY),
            1,
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        ),
        BudgetPeriods(
            buildBudget(BreakpointEnum.MONTHLY),
            1,
            LocalDateTime.parse("2020-02-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-02-28T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        )
    )

    fun buildSetOfBudgetPeriods(): Set<BudgetPeriods> = setOf(
        BudgetPeriods(
            buildBudget(BreakpointEnum.MONTHLY),
            1,
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        ),
        BudgetPeriods(
            buildBudget(BreakpointEnum.MONTHLY),
            1,
            LocalDateTime.parse("2020-02-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-02-28T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        )
    )

    fun buildListOfExpense(): List<Expense> =
        listOf(Expense(1, buildCategories()[0], buildUser(), "Expense", BigDecimal.valueOf(100), LocalDateTime.now(), "Expense description"))
}
