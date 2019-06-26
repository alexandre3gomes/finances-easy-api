package net.finance.service;

import java.util.Date;

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
import net.finance.bo.ExpenseBo;
import net.finance.entity.Category;
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
		return new ResponseEntity<>(expenseBo.get(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Expense>> list(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "10") final int size,
			@RequestParam(defaultValue = "expireAt") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction) {
		return new ResponseEntity<>(expenseBo.list(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Expense> update(@RequestBody final Expense dev) {
		return new ResponseEntity<>(expenseBo.update(dev), HttpStatus.OK);
	}

	@GetMapping("/list/{categoryId}")
	public ResponseEntity<Page<Expense>> listByCategory(@PathVariable("categoryId") final Integer categoryId,
			@RequestParam(defaultValue = "0") final int page, @RequestParam(defaultValue = "10") final int size,
			@RequestParam(defaultValue = "expireAt") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction) {
		return new ResponseEntity<>(expenseBo.getExpenseByCategory(new Category(categoryId),
				PageRequest.of(page, size, new Sort(direction, order))), HttpStatus.OK);
	}

	@GetMapping("/list/{categoryId}/{startDate}/{endDate}")
	public ResponseEntity<Page<Expense>> listByCategoryAndDates(@PathVariable("categoryId") final Integer categoryId,
			@PathVariable("startDate") final Date startDate, @PathVariable("endDate") final Date endDate,
			@RequestParam(defaultValue = "0") final int page, @RequestParam(defaultValue = "10") final int size,
			@RequestParam(defaultValue = "expireAt") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction) {
		return new ResponseEntity<>(expenseBo.getExpenseByCategoryAndDates(new Category(categoryId), startDate, endDate,
				PageRequest.of(page, size, new Sort(direction, order))), HttpStatus.OK);
	}

}
