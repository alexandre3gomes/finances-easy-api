package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;
import net.finance.dao.BudgetDao;
import net.finance.entity.Budget;

public class BudgetBo implements GenericBo<Budget> {

	@NonNull
	private final BudgetDao budgetDao;

	@Autowired
	public BudgetBo(final BudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}

	@Override
	public Budget create(Budget budget) {
		return budgetDao.create(budget);
	}

	@Override
	public void delete(Integer id) {
		budgetDao.delete(id);
	}

	@Override
	public Budget findById(Integer id) {
		return budgetDao.find(id);
	}

	@Override
	public List<Budget> listAll() {
		return budgetDao.list();
	}

	@Override
	public Budget update(Budget budget) {
		return budgetDao.update(budget);
	}

}
