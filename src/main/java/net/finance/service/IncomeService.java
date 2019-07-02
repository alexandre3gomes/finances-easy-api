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
import net.finance.bo.IncomeBo;
import net.finance.entity.Income;

@RestController
@RequestMapping("/income")
public class IncomeService {

	@NonNull
	private final IncomeBo incomeBo;

	@Autowired
	public IncomeService(final IncomeBo incomeBo) {
		this.incomeBo = incomeBo;
	}

	@PostMapping("")
	public ResponseEntity<Income> create(@RequestBody final Income income) {
		return new ResponseEntity<>(incomeBo.create(income), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		incomeBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Income> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(incomeBo.get(id), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<Page<Income>> list(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "10") final int size, @RequestParam(defaultValue = "date") final String order,
			@RequestParam(defaultValue = "DESC") final Sort.Direction direction) {
		return new ResponseEntity<>(incomeBo.list(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Income> update(@RequestBody final Income income) {
		return new ResponseEntity<>(incomeBo.update(income), HttpStatus.OK);
	}

}
