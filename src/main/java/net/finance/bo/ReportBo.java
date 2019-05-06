package net.finance.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Category;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;

@Service
@Transactional
public class ReportBo {

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	BudgetRepository budgetRepository;

	public List<CategoryAggregValuesDto> byCategory(final Integer budgetId) {
		final List<CategoryAggregValuesDto> ret = new ArrayList<>();
		final Optional<List<Category>> categories = budgetRepository.getCategoriesByBudget(budgetId);
		for (final Category cat : categories.get()) {
			final CategoryAggregValuesDto catValues = new CategoryAggregValuesDto();
			catValues.setCategory(cat);
			catValues.setPeriodValue(expenseRepository.getValuesByCategories(budgetId, cat.getId()));
			ret.add(catValues);
		}
		return ret;
	}

}
