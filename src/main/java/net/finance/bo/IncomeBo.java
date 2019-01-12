package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import net.finance.dao.IncomeDao;
import net.finance.entity.Income;

@Service
@Transactional
public class IncomeBo implements GenericBo<Income> {

	@NonNull
	IncomeDao incDao;

	@Autowired
	public IncomeBo(final IncomeDao incDao) {
		this.incDao = incDao;
	}

	@Override
	public Income create(final Income inc) {
		return incDao.create(inc);
	}

	@Override
	public void delete(final Integer id) {
		incDao.delete(id);
	}

	@Override
	public Income findById(final Integer id) {
		return incDao.find(id);
	}

	@Override
	public List<Income> listAll() {
		return incDao.list();
	}

	@Override
	public Income update(final Income inc) {
		return incDao.update(inc);
	}

}
