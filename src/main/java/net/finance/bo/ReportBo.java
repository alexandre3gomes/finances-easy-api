package net.finance.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Category;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import net.finance.repository.ExpenseRepository;

@Service
@Transactional
public class ReportBo {

	ExpenseRepository expenseRepository;

	BudgetRepository budgetRepository;

	CategoryRepository categoryRepository;

	@Autowired
	public ReportBo(final ExpenseRepository expenseRepository, final BudgetRepository budgetRepository,
			final CategoryRepository categoryRepository) {
		this.expenseRepository = expenseRepository;
		this.budgetRepository = budgetRepository;
		this.categoryRepository = categoryRepository;
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

}
