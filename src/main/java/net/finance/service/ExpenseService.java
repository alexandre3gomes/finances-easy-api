package net.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import net.finance.entity.Expense;
import net.finance.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
public class ExpenseService {

	@NonNull
	private ExpenseRepository expenseRep;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRep) {
		this.expenseRep = expenseRep;
	}

	@PostMapping("/create")
	public ResponseEntity<Expense> create(@RequestBody final Expense expense) {
		return new ResponseEntity<>(expenseRep.save(expense), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		expenseRep.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Expense> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(expenseRep.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Expense>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "expireAt") String order,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction) {
		return new ResponseEntity<>(expenseRep.findAll(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Expense> update(@RequestBody final Expense dev) {
		return new ResponseEntity<>(expenseRep.save(dev), HttpStatus.OK);
	}

}
