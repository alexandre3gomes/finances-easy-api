package com.finances.controller;

import com.finances.bo.BudgetBo;
import com.finances.bo.ExpenseBo;
import com.finances.bo.IncomeBo;
import com.finances.bo.SavingsBo;
import com.finances.dto.report.CategoryAggregValuesDto;
import com.finances.entity.Expense;
import com.finances.entity.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final ExpenseBo expenseBo;
    private final IncomeBo incomeBo;
    private final BudgetBo budgetBo;
    private final SavingsBo savingsBo;

    @Autowired
    public DashboardController(ExpenseBo expenseBo, IncomeBo incomeBo, BudgetBo budgetBo, SavingsBo savingsBo) {
        this.expenseBo = expenseBo;
        this.incomeBo = incomeBo;
        this.budgetBo = budgetBo;
        this.savingsBo = savingsBo;
    }

    @GetMapping("/actualExpense")
    public ResponseEntity<List<Expense>> getActualExpense() {
        Optional<List<Expense>> optExpenses = expenseBo.getActualExpense();
        List<Expense> expenses = optExpenses.orElseThrow(NoSuchElementException::new);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/actualIncome")
    public ResponseEntity<List<Income>> sumIncome() {
        Optional<List<Income>> optIncomes = incomeBo.getActualIncome();
        List<Income> incomes = optIncomes.orElseThrow(NoSuchElementException::new);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping("/actualBalance")
    public ResponseEntity<List<CategoryAggregValuesDto>> getActualBalance() {
        return new ResponseEntity<>(budgetBo.getActualBalance(), HttpStatus.OK);
    }

    @GetMapping("/totalSavings")
    public ResponseEntity<BigDecimal> getTotal() {
        return new ResponseEntity<>(savingsBo.getTotalSavings(), HttpStatus.OK);
    }

}
