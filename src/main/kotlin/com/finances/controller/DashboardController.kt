package com.finances.controller

import com.finances.bo.BudgetBo
import com.finances.bo.ExpenseBo
import com.finances.bo.IncomeBo
import com.finances.bo.SavingsBo
import com.finances.dto.ExpenseDTO
import com.finances.dto.IncomeDTO
import com.finances.dto.report.CategoryAggregValuesDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/dashboard")
class DashboardController(private val expenseBo: ExpenseBo, private val incomeBo: IncomeBo, private val budgetBo: BudgetBo, private val savingsBo: SavingsBo) {

    @GetMapping("/actualExpense")
    fun actualExpense(): List<ExpenseDTO> = expenseBo.actualExpense

    @GetMapping("/actualIncome")
    fun sumIncome(): List<IncomeDTO> = incomeBo.actualIncome

    @GetMapping("/actualBalance")
    fun actualBalance(): List<CategoryAggregValuesDto> = budgetBo.actualBalance

    @GetMapping("/totalSavings")
    fun total(): BigDecimal = savingsBo.totalSavings
}
