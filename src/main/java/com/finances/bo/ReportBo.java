package com.finances.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.finances.entity.BudgetPeriods;
import com.finances.entity.Category;
import com.finances.repository.BudgetRepository;
import com.finances.repository.CategoryRepository;
import com.finances.repository.ExpenseRepository;
import com.finances.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finances.dto.report.CategoryAggregValuesDto;
import com.finances.entity.Income;

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
