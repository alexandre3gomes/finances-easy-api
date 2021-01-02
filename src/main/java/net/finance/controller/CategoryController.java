package net.finance.controller;

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
import net.finance.bo.CategoryBo;
import net.finance.entity.Category;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@NonNull
	private final CategoryBo categoryBo;

	@Autowired
	public CategoryController(final CategoryBo categoryBo) {
		this.categoryBo = categoryBo;
	}

	@PostMapping("")
	public ResponseEntity<Category> create(@RequestBody final Category cat) {
		return new ResponseEntity<>(categoryBo.create(cat), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		categoryBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(categoryBo.get(id), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<Page<Category>> list(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "10") final int size, @RequestParam(defaultValue = "name") final String order,
			@RequestParam(defaultValue = "ASC") final Sort.Direction direction) {
		return new ResponseEntity<>(categoryBo.list(PageRequest.of(page, size, Sort.by(direction, order))),
				HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Category> update(@RequestBody final Category cat) {
		return new ResponseEntity<>(categoryBo.update(cat), HttpStatus.OK);
	}

}
