package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;
import net.finance.dao.ExpenseDao;
import net.finance.entity.Expense;

public class ExpenseBo implements GenericBo<Expense> {

	@NonNull
	ExpenseDao expDao;

	@Autowired
	public ExpenseBo(ExpenseDao expDao) {
		this.expDao = expDao;
	}

	@Override
	public Expense create(Expense exp) {
		return expDao.create(exp);
	}

	@Override
	public void delete(Integer id) {
		expDao.delete(id);
	}

	@Override
	public Expense findById(Integer id) {
		return expDao.find(id);
	}

	@Override
	public List<Expense> listAll() {
		return expDao.list();
	}

	@Override
	public Expense update(Expense exp) {
		return expDao.update(exp);
	}

}
