package net.finance.controller;

import net.finance.bo.BudgetBo;
import net.finance.bo.ExpenseBo;
import net.finance.bo.IncomeBo;
import net.finance.bo.SavingsBo;
import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Expense;
import net.finance.entity.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        Optional<List<Expense>> optExpenses = expenseBo.getActualExpense(PageRequest.of(0, Integer.MAX_VALUE));
        List<Expense> expenses = new ArrayList<>();
        if (optExpenses.isPresent()) expenses = optExpenses.get();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/actualIncome")
    public ResponseEntity<List<Income>> sumIncome() {
        Optional<List<Income>> optIncomes = incomeBo.getActualIncome();
        List<Income> incomes = new ArrayList<>();
        if (optIncomes.isPresent()) incomes = optIncomes.get();
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
