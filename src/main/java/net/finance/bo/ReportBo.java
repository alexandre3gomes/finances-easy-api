package net.finance.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.dto.CategoryGroupedValuesDTO;
import net.finance.repository.ExpenseRepository;

@Service
@Transactional
public class ReportBo {

	@Autowired
	ExpenseRepository expenseRepository;

	public List<CategoryGroupedValuesDTO> byCategory(Integer budgetId) {
		return expenseRepository.getValuesByCategories(budgetId);
	}

}
