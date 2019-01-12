package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import net.finance.dao.ExpenseDao;
import net.finance.entity.Expense;

@Service
@Transactional
public class ExpenseBo implements GenericBo<Expense> {

	@NonNull
	ExpenseDao expDao;

	@Autowired
	public ExpenseBo(final ExpenseDao expDao) {
		this.expDao = expDao;
	}

	@Override
	public Expense create(final Expense exp) {
		return expDao.create(exp);
	}

	@Override
	public void delete(final Integer id) {
		expDao.delete(id);
	}

	@Override
	public Expense findById(final Integer id) {
		return expDao.find(id);
	}

	@Override
	public List<Expense> listAll() {
		return expDao.list();
	}

	@Override
	public Expense update(final Expense exp) {
		return expDao.update(exp);
	}

}
