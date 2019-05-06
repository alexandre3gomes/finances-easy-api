package net.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.finance.bo.ExpenseBo;
import net.finance.bo.IncomeBo;
import net.finance.entity.Expense;
import net.finance.entity.Income;

@RestController
@RequestMapping("/dashboard")
public class DashboardService {

	private final ExpenseBo expenseBo;
	private final IncomeBo incomeBo;

	@Autowired
	public DashboardService(final ExpenseBo expenseBo, final IncomeBo incomeBo) {
		this.expenseBo = expenseBo;
		this.incomeBo = incomeBo;

	}

	@GetMapping("/actualExpense")
	public ResponseEntity<List<Expense>> getActualExpense() {
		final Optional<List<Expense>> optExpenses = expenseBo.getActualExpense();
		List<Expense> expenses = new ArrayList<>();
		if (optExpenses.isPresent()) {
			expenses = optExpenses.get();
		}
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}

	@GetMapping("/actualIncome")
	public ResponseEntity<List<Income>> sumIncome() {
		final Optional<List<Income>> optIncomes = incomeBo.getActualIncome();
		List<Income> incomes = new ArrayList<>();
		if (optIncomes.isPresent()) {
			incomes = optIncomes.get();
		}
		return new ResponseEntity<>(incomes, HttpStatus.OK);
	}

}
