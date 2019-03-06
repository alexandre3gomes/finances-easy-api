package net.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import net.finance.bo.CategoryBo;
import net.finance.entity.Category;

@RestController
@RequestMapping("/category")
public class CategoryService {

	@NonNull
	private final CategoryBo categoryBo;

	@Autowired
	public CategoryService(final CategoryBo categoryBo) {
		this.categoryBo = categoryBo;
	}

	@PostMapping("/create")
	public ResponseEntity<Category> create(@RequestBody final Category budget) {
		return new ResponseEntity<>(categoryBo.create(budget), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		categoryBo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Category> get(@PathVariable("id") final Integer id) {
		return new ResponseEntity<>(categoryBo.findById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Category>> list() {
		return new ResponseEntity<>(categoryBo.listAll(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Category> update(@RequestBody final Category dev) {
		return new ResponseEntity<>(categoryBo.update(dev), HttpStatus.OK);
	}

}
