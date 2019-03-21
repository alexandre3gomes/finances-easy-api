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
import net.finance.entity.Category;
import net.finance.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryService {

	@NonNull
	private final CategoryRepository categoryRep;

	@Autowired
	public CategoryService(final CategoryRepository categoryRep) {
		this.categoryRep = categoryRep;
	}

	@PostMapping("/create")
	public ResponseEntity<Category> create(@RequestBody final Category budget) {
		return new ResponseEntity<>(categoryRep.save(budget), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		categoryRep.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Category> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(categoryRep.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Category>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String order,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction) {
		return new ResponseEntity<>(categoryRep.findAll(PageRequest.of(page, size, new Sort(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Category> update(@RequestBody final Category cat) {
		return new ResponseEntity<>(categoryRep.save(cat), HttpStatus.OK);
	}

}
