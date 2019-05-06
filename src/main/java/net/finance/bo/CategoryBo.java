package net.finance.bo;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import net.finance.entity.Category;
import net.finance.repository.CategoryRepository;

@Service
@Transactional
public class CategoryBo {

	@NonNull
	private final CategoryRepository categoryRep;

	@Autowired
	public CategoryBo(final CategoryRepository categoryRep) {
		this.categoryRep = categoryRep;
	}

	public Category create(final Category budget) {
		return categoryRep.save(budget);
	}

	public void delete(final Integer id) {
		categoryRep.deleteById(id);
	}

	public Category get(final Integer id) {
		return categoryRep.findById(id).get();
	}

	public Page<Category> list(final PageRequest pageReq) {
		return categoryRep.findAll(pageReq);
	}

	public Category update(final Category cat) {
		return categoryRep.save(cat);
	}

}
