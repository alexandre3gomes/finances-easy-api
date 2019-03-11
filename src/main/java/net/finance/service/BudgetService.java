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
import net.finance.CategoryValueDTO;
import net.finance.entity.Budget;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;

@RestController
@RequestMapping("/budget")
public class BudgetService {

	@NonNull
	private final BudgetRepository budgetRep;

	@NonNull
	private final CategoryRepository catRep;

	@Autowired
	public BudgetService(final BudgetRepository budgetRepository, final CategoryRepository catRep) {
		this.budgetRep = budgetRepository;
		this.catRep = catRep;
	}

	@PostMapping("/create")
	public ResponseEntity<Budget> create(@RequestBody final Budget budget) {
		for (final CategoryValueDTO catVal : budget.getCategories()) {
			budget.addCategory(catRep.findById(catVal.getCategory().getId()).get(), catVal.getValue());
		}
		return new ResponseEntity<>(budgetRep.save(budget), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		budgetRep.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Budget> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(budgetRep.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Budget>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "startDate") String order,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction) {
		return new ResponseEntity<>(budgetRep.findAll(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Budget> update(@RequestBody final Budget budget) {
		return new ResponseEntity<>(budgetRep.save(budget), HttpStatus.OK);
	}

}
