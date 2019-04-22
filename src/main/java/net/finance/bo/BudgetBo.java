package net.finance.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
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
		return new Budget(budget.getUser(), budget.getStartDate(), budget.getEndDate(), budCat);
	}

}
