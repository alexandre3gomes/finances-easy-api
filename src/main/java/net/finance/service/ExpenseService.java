package net.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import net.finance.bo.ExpenseBo;
import net.finance.entity.Expense;

@RestController
@RequestMapping("/expense")
public class ExpenseService {

	@NonNull
	private final ExpenseBo expenseBo;

	@Autowired
	public ExpenseService(final ExpenseBo expenseBo) {
		this.expenseBo = expenseBo;
	}

	@PostMapping("/create")
	public ResponseEntity<Expense> create(@RequestBody final Expense expense) {
		return new ResponseEntity<>(expenseBo.create(expense), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		expenseBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Expense> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(expenseBo.findById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Expense>> list() {
		return new ResponseEntity<>(expenseBo.listAll(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Expense> update(@RequestBody final Expense dev) {
		return new ResponseEntity<>(expenseBo.update(dev), HttpStatus.OK);
	}

}
