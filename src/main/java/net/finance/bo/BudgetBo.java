package net.finance.bo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
import net.finance.entity.BudgetPeriods;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;

@Service
@Transactional
public class BudgetBo {

	BudgetRepository budgetRepository;

	@Autowired
	public BudgetBo(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}

	public Budget create(final Budget budget) {
		return budgetRepository.save(getNewBudget(budget));
	}

	public void delete(final Integer id) {
		budgetRepository.deleteById(id);
	}

	public Budget get(final Integer id) {
		return budgetRepository.findById(id).get();
	}

	public Page<Budget> list(PageRequest pageRequest) {
		return budgetRepository.findAll(pageRequest);
	}

	public Budget update(final Budget budget) {
		budgetRepository.deleteById(budget.getId());
		return budgetRepository.save(getNewBudget(budget));
	}

	private Budget getNewBudget(Budget budget) {
		final BudgetCategories[] budCat = budget.getCategories().stream().toArray(BudgetCategories[]::new);
		final Budget newBudget = new Budget(budget.getUser(), budget.getStartDate(), budget.getEndDate(),
				budget.getBreakpoint(), splitInMonths(budget), budCat);
		return newBudget;
	}

	private Set<BudgetPeriods> splitInMonths(Budget budget) {
		final Set<BudgetPeriods> periods = new HashSet<>();
		DateTime sDate = new DateTime(budget.getStartDate());
		final DateTime eDate = new DateTime(budget.getEndDate());
		int idPeriod = 0;
		while (sDate.isBefore(eDate)) {
			final BudgetPeriods budPed = new BudgetPeriods();
			budPed.setBudget(budget);
			budPed.setIdPeriod(idPeriod);
			budPed.setStartDate(sDate.hourOfDay().withMinimumValue().minuteOfDay().withMinimumValue().secondOfDay()
					.withMinimumValue().toDate());
			Date finalDate = null;
			if (budget.getBreakpoint() == BreakpointEnum.MONTHLY.getId()) {
				finalDate = sDate.dayOfMonth().withMaximumValue().hourOfDay().withMaximumValue().minuteOfDay()
						.withMaximumValue().secondOfDay().withMaximumValue().toDate();
				sDate = sDate.plusMonths(1);
			} else {
				sDate = sDate.plusWeeks(1);
				finalDate = sDate.plusDays(-1).hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue()
						.secondOfDay().withMaximumValue().toDate();
			}
			budPed.setEndDate(finalDate);
			periods.add(budPed);
			idPeriod++;
		}
		return periods;
	}
}
