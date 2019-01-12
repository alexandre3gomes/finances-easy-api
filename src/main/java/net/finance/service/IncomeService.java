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

	@PutMapping("/create")
	public ResponseEntity<Income> create(@RequestBody final Income income) {
		return new ResponseEntity<>(incomeBo.create(income), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		incomeBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Income> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(incomeBo.findById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Income>> list() {
		return new ResponseEntity<>(incomeBo.listAll(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Income> update(@RequestBody final Income dev) {
		return new ResponseEntity<>(incomeBo.update(dev), HttpStatus.OK);
	}

}
