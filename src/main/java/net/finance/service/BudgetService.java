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
import net.finance.bo.BudgetBo;
import net.finance.entity.Budget;

@RestController
@RequestMapping("/budget")
public class BudgetService {

	@NonNull
	private final BudgetBo budgetBo;

	@Autowired
	public BudgetService(final BudgetBo budgetRepository) {
		budgetBo = budgetRepository;
	}

	@PostMapping("/create")
	public ResponseEntity<Budget> create(@RequestBody final Budget budget) {
		return new ResponseEntity<>(budgetBo.create(budget), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		budgetBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Budget> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(budgetBo.get(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Budget>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "startDate") String order,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction) {
		return new ResponseEntity<>(budgetBo.list(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Budget> update(@RequestBody final Budget budget) {
		return new ResponseEntity<>(budgetBo.update(budget), HttpStatus.OK);
	}

}
