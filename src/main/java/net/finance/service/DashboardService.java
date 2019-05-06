package net.finance.service;

import java.util.List;

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
		return new ResponseEntity<>(expenseBo.getActualExpense(), HttpStatus.OK);
	}

	@GetMapping("/actualIncome")
	public ResponseEntity<List<Income>> sumIncome() {
		return new ResponseEntity<>(incomeBo.getActualIncome(), HttpStatus.OK);
	}

}
