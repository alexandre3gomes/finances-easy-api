package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;
import net.finance.dao.CategoryDao;
import net.finance.entity.Category;

public class CategoryBo implements GenericBo<Category> {

	@NonNull
	private final CategoryDao catDao;

	@Autowired
	public CategoryBo(CategoryDao catDao) {
		this.catDao = catDao;
	}

	@Override
	public Category create(Category cat) {
		return catDao.create(cat);
	}

	@Override
	public void delete(Integer id) {
		catDao.delete(id);
	}

	@Override
	public Category findById(Integer id) {
		return catDao.find(id);
	}

	@Override
	public List<Category> listAll() {
		return catDao.list();
	}

	@Override
	public Category update(Category cat) {
		return catDao.update(cat);
	}

}
