package net.finance.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
import net.finance.bo.ExpenseBo;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.entity.Expense;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@NonNull
	private final ExpenseBo expenseBo;

	@Autowired
	public ExpenseController(final ExpenseBo expenseBo) {
		this.expenseBo = expenseBo;
	}

	@PostMapping("")
	public ResponseEntity<Expense> create(@RequestBody final Expense expense) {
		return new ResponseEntity<>(expenseBo.create(expense), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		expenseBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Expense> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(expenseBo.get(id), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<Page<Expense>> list(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "10") final int size,
			@RequestParam(defaultValue = "expireAt") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction,
			@RequestParam(name = "category", required = false) final Integer category,
			@RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final Date startDate,
			@RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final Date endDate,
			@RequestParam(name = "name", required = false) final String name) {
		final ExpenseFilterDTO expFilter = new ExpenseFilterDTO(category, startDate, endDate, name);
		return new ResponseEntity<>(expenseBo.list(expFilter, PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Expense> update(@RequestBody final Expense dev) {
		return new ResponseEntity<>(expenseBo.update(dev), HttpStatus.OK);
	}

}
