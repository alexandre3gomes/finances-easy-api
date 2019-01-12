package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import net.finance.dao.CategoryDao;
import net.finance.entity.Category;

@Service
@Transactional
public class CategoryBo implements GenericBo<Category> {

	@NonNull
	private final CategoryDao catDao;

	@Autowired
	public CategoryBo(final CategoryDao catDao) {
		this.catDao = catDao;
	}

	@Override
	public Category create(final Category cat) {
		return catDao.create(cat);
	}

	@Override
	public void delete(final Integer id) {
		catDao.delete(id);
	}

	@Override
	public Category findById(final Integer id) {
		return catDao.find(id);
	}

	@Override
	public List<Category> listAll() {
		return catDao.list();
	}

	@Override
	public Category update(final Category cat) {
		return catDao.update(cat);
	}

}
