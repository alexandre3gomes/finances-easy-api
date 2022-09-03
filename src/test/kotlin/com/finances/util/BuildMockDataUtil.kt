package com.finances.util

import com.finances.dto.ExpenseFilterDTO
import com.finances.dto.report.PeriodValueDto
import com.finances.entity.*
import com.finances.enums.BreakpointEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.projection.SpelAwareProxyProjectionFactory
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object BuildMockDataUtil {

    fun buildListOfCategories() = listOf(Category(1, "Test 1", false), Category(1, "Test 2", false))

    fun buildListOfExpense(): List<Expense> =
        listOf(Expense(1, buildListOfCategories()[0], buildUser(), "Expense", BigDecimal.valueOf(100), LocalDateTime.now(), "Expense description"))

    fun buildListOfIncomes(): List<Income> {
        return listOf(Income(1, buildUser(), "Income", BigDecimal.valueOf(100), LocalDateTime.now(), "Income description"))
    }

    fun buildListOfSavings(): List<Savings> =
        listOf(Savings(buildUser(), "Savings test", BigDecimal.TEN, LocalDateTime.now()))

    fun buildListOfBudgets(breakpoint: BreakpointEnum): List<Budget> {
        val budget = Budget(
            buildUser(),
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME),
            breakpoint.id
        )
        budget.id = 1
        buildSetOfBudgetCategories().forEach {
            budget.categories.add(it)
        }
        return listOf(budget)
    }

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
        val factory = SpelAwareProxyProjectionFactory()
        val values = mutableMapOf("actualValue" to BigDecimal.valueOf(150).toString())
        values["plannedValue"] = BigDecimal.valueOf(150).toString()
        values["startDate"] = LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME).toString()
        values["endDate"] = LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME).toString()
        val periodValueDto = factory.createProjection(PeriodValueDto::class.java, values)
        return listOf(periodValueDto)
    }

    fun buildBudgetPeriods(): BudgetPeriods = BudgetPeriods(
        buildListOfBudgets(BreakpointEnum.MONTHLY)[0],
        1,
        LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
    )

    fun buildSetOfBudgetCategories(): Set<BudgetCategories> {
        val catBud = BudgetCategories(
            buildListOfCategories()[0],
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

    fun buildUser() = User("Test user", "test")

    fun buildListOfBudgetPeriods(): List<BudgetPeriods> = listOf(
        BudgetPeriods(
            buildListOfBudgets(BreakpointEnum.MONTHLY)[0],
            1,
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        ),
        BudgetPeriods(
            buildListOfBudgets(BreakpointEnum.MONTHLY)[0],
            1,
            LocalDateTime.parse("2020-02-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-02-28T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        )
    )

    fun buildSetOfBudgetPeriods(): Set<BudgetPeriods> = setOf(
        BudgetPeriods(
            buildListOfBudgets(BreakpointEnum.MONTHLY)[0],
            1,
            LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        ),
        BudgetPeriods(
            buildListOfBudgets(BreakpointEnum.MONTHLY)[0],
            1,
            LocalDateTime.parse("2020-02-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME),
            LocalDateTime.parse("2020-02-28T23:59:59", DateTimeFormatter.ISO_DATE_TIME)
        )
    )
}
