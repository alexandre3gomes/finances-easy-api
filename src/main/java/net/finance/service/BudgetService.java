package net.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import net.finance.CategoryValueDTO;
import net.finance.bo.BudgetBo;
import net.finance.bo.CategoryBo;
import net.finance.entity.Budget;

@RestController
@RequestMapping("/budget")
public class BudgetService {

	@NonNull
	private final BudgetBo budgetBo;

	@NonNull
	private final CategoryBo catBo;

	@Autowired
	public BudgetService(final BudgetBo budgetBo, final CategoryBo catBo) {
		this.budgetBo = budgetBo;
		this.catBo = catBo;
	}

	@PutMapping("/create")
	public ResponseEntity<Budget> create(@RequestBody final Budget budget) {
		for (final CategoryValueDTO catVal : budget.getCategories()) {
			budget.addCategory(catBo.findById(catVal.getCategory().getId()), catVal.getValue());
		}
		return new ResponseEntity<>(budgetBo.create(budget), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		budgetBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Budget> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(budgetBo.findById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Budget>> list() {
		return new ResponseEntity<>(budgetBo.listAll(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Budget> update(@RequestBody final Budget budget) {
		return new ResponseEntity<>(budgetBo.update(budget), HttpStatus.OK);
	}

}
