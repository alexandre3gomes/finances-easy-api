package net.finance.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Income;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import net.finance.repository.ExpenseRepository;
import net.finance.repository.IncomeRepository;

@Service
@Transactional
public class ReportBo {

	ExpenseRepository expenseRepository;

	BudgetRepository budgetRepository;

	CategoryRepository categoryRepository;

	IncomeRepository incomeRepository;

	@Autowired
	public ReportBo(final ExpenseRepository expenseRepository, final BudgetRepository budgetRepository,
			final CategoryRepository categoryRepository, final IncomeRepository incomeRepository) {
		this.expenseRepository = expenseRepository;
		this.budgetRepository = budgetRepository;
		this.categoryRepository = categoryRepository;
		this.incomeRepository = incomeRepository;
	}

	public List<CategoryAggregValuesDto> byCategory(final Integer budgetId) {
		final List<CategoryAggregValuesDto> ret = new ArrayList<>();
		final List<Category> categories = categoryRepository.getCategoriesByBudget(budgetId);
		for (final Category cat : categories) {
			final CategoryAggregValuesDto catValues = new CategoryAggregValuesDto();
			catValues.setCategory(cat);
			catValues.setPeriodValue(budgetRepository.getValuesByCategories(budgetId, cat.getId()));
			ret.add(catValues);
		}
		return ret;
	}

	public List<BigDecimal> incomeByPeriod(final Integer budgetId) {
		final List<BigDecimal> ret = new ArrayList<>();
		final List<BudgetPeriods> periods = budgetRepository.getPeriodsByBudget(budgetId);
		for (final BudgetPeriods per : periods) {
			final Optional<List<Income>> listIncome = incomeRepository.findByDateBetween(per.getStartDate(),
					per.getEndDate());
			if (listIncome.isPresent()) {
				ret.add(listIncome.get().stream().map(inc -> inc.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
			} else {
				ret.add(BigDecimal.ZERO);
			}
		}
		return ret;
	}

}
