package net.finance.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Income;
import net.finance.repository.BudgetRepository;
import net.finance.repository.IncomeRepository;

@Service
@Transactional
public class IncomeBo {

	@NonNull
	private final IncomeRepository incomeRepository;

	@NonNull
	private final BudgetRepository budgetRepository;

	@Autowired
	public IncomeBo(final IncomeRepository incomeRep, final BudgetRepository budgetRep) {
		incomeRepository = incomeRep;
		budgetRepository = budgetRep;
	}

	public Income create(final Income income) {
		return incomeRepository.save(income);
	}

	public void delete(final Integer id) {
		incomeRepository.deleteById(id);
	}

	public Income get(final Integer id) {
		return incomeRepository.findById(id).get();
	}

	public Page<Income> list(final PageRequest pageReq) {
		return incomeRepository.findAll(pageReq);
	}

	public Income update(final Income income) {
		return incomeRepository.save(income);
	}

	public List<Income> getActualIncome() {
		final Date now = Calendar.getInstance().getTime();
		final BudgetPeriods period = budgetRepository.getPeriodsByDate(now);
		return incomeRepository.findByDateBetween(period.getStartDate(), period.getEndDate());
	}

}
