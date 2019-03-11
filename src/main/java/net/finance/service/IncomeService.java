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
import net.finance.entity.Income;
import net.finance.repository.IncomeRepository;

@RestController
@RequestMapping("/income")
public class IncomeService {

	@NonNull
	private final IncomeRepository incomeRep;

	@Autowired
	public IncomeService(final IncomeRepository incomeRep) {
		this.incomeRep = incomeRep;
	}

	@PostMapping("/create")
	public ResponseEntity<Income> create(@RequestBody final Income income) {
		return new ResponseEntity<>(incomeRep.save(income), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		incomeRep.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Income> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(incomeRep.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Income>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "date") String order,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction) {
		return new ResponseEntity<>(incomeRep.findAll(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Income> update(@RequestBody final Income income) {
		return new ResponseEntity<>(incomeRep.save(income), HttpStatus.OK);
	}

}
