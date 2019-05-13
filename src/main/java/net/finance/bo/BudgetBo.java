package net.finance.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;

@Service
@Transactional
public class BudgetBo {

	BudgetRepository budgetRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public BudgetBo(final BudgetRepository budgetRepository, final CategoryRepository categoryRepository) {
		this.budgetRepository = budgetRepository;
		this.categoryRepository = categoryRepository;
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

	public Page<Budget> list(final PageRequest pageRequest) {
		return budgetRepository.findAll(pageRequest);
	}

	public Budget update(final Budget budget) {
		budgetRepository.deleteById(budget.getId());
		return budgetRepository.save(getNewBudget(budget));
	}

	public List<CategoryAggregValuesDto> getActualBalance() {
		final List<CategoryAggregValuesDto> ret = new ArrayList<>();
		final Date now = Calendar.getInstance().getTime();
		final List<Category> categories = categoryRepository.getCategoriesByPeriod(now);
		for (final Category cat : categories) {
			final CategoryAggregValuesDto catAg = new CategoryAggregValuesDto();
			catAg.setCategory(cat);
			catAg.setPeriodValue(budgetRepository.getAggregateValueByDate(now, cat.getId()));
			ret.add(catAg);
		}
		return ret;
	}

	private Budget getNewBudget(final Budget budget) {
		final BudgetCategories[] budCat = budget.getCategories().stream().toArray(BudgetCategories[]::new);
		final Budget newBudget = new Budget(budget.getUser(), budget.getStartDate(), budget.getEndDate(),
				budget.getBreakPeriod(), splitInMonths(budget), budCat);
		return newBudget;
	}

	private static Set<BudgetPeriods> splitInMonths(final Budget budget) {
		final Set<BudgetPeriods> periods = new TreeSet<>();
		LocalDateTime sDate = new LocalDateTime(budget.getStartDate());
		final LocalDateTime eDate = new LocalDateTime(budget.getEndDate());
		int idPeriod = 0;
		while (sDate.isBefore(eDate)) {
			final BudgetPeriods budPed = new BudgetPeriods();
			budPed.setBudget(budget);
			budPed.setIdPeriod(idPeriod);
			budPed.setStartDate(sDate.toDate());
			Date finalDate = null;
			if (budget.getBreakPeriod() == BreakpointEnum.MONTHLY.getId()) {
				finalDate = sDate.plusDays(-1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
						.plusMonths(1).toDate();
				sDate = new LocalDateTime(finalDate).plusDays(1).withHourOfDay(0).withMinuteOfHour(0)
						.withSecondOfMinute(0);
			} else {
				sDate = sDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).plusWeeks(1);
				finalDate = sDate.hourOfDay().withMaximumValue().minuteOfHour().withMaximumValue().secondOfMinute()
						.withMaximumValue().plusDays(-1).toDate();
			}
			budPed.setEndDate(finalDate);
			periods.add(budPed);
			idPeriod++;
		}
		return periods;
	}
}
