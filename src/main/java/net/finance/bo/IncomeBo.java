package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;
import net.finance.dao.IncomeDao;
import net.finance.entity.Income;

public class IncomeBo implements GenericBo<Income> {

	@NonNull
	IncomeDao incDao;

	@Autowired
	public IncomeBo(IncomeDao incDao) {
		this.incDao = incDao;
	}

	@Override
	public Income create(Income inc) {
		return incDao.create(inc);
	}

	@Override
	public void delete(Integer id) {
		incDao.delete(id);
	}

	@Override
	public Income findById(Integer id) {
		return incDao.find(id);
	}

	@Override
	public List<Income> listAll() {
		return incDao.list();
	}

	@Override
	public Income update(Income inc) {
		return incDao.update(inc);
	}

}
