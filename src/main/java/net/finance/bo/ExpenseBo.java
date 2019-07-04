package net.finance.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;

@Service
@Transactional
public class ExpenseBo {

	@NonNull
	private final ExpenseRepository expenseRepository;
	private final BudgetRepository budgetRepository;

	@Autowired
	public ExpenseBo(final ExpenseRepository expenseRep, final BudgetRepository budgetRepository) {
		expenseRepository = expenseRep;
		this.budgetRepository = budgetRepository;
	}

	public Expense create(final Expense expense) {
		return expenseRepository.save(expense);
	}

	public void delete(final Integer id) {
		expenseRepository.deleteById(id);
	}

	public Expense get(final Integer id) {
		return expenseRepository.findById(id).get();
	}

	public Page<Expense> list(final PageRequest pageReq, final ExpenseFilterDTO expFilter) {
		if (expFilter.getCategoryId() == null && (expFilter.getStartDate() == null || expFilter.getEndDate() == null)) {
			return expenseRepository.findAll(pageReq);
		} else if (expFilter.getCategoryId() != null
				&& (expFilter.getStartDate() == null || expFilter.getEndDate() == null)) {
			return expenseRepository.findByCategory(new Category(expFilter.getCategoryId()), pageReq);
		} else {
			return expenseRepository.findByCategoryAndExpireAtBetween(new Category(expFilter.getCategoryId()),
					expFilter.getStartDate(), expFilter.getEndDate(), pageReq);
		}
	}

	public Expense update(final Expense dev) {
		return expenseRepository.save(dev);
	}

	public Optional<List<Expense>> getActualExpense() {
		final Date now = Calendar.getInstance().getTime();
		final Optional<BudgetPeriods> period = budgetRepository.getPeriodsByDate(now);
		if (period.isPresent()) {
			return expenseRepository.findByExpireAtBetween(period.get().getStartDate(), period.get().getEndDate());
		} else {
			return Optional.empty();
		}
	}

	public Page<Expense> getExpenseByCategory(final Category category, final PageRequest page) {
		return expenseRepository.findByCategory(category, page);
	}

	public Page<Expense> getExpenseByCategoryAndDates(final Category category, final Date startDate, final Date endDate,
			final PageRequest page) {
		return expenseRepository.findByCategoryAndExpireAtBetween(category, startDate, endDate, page);
	}

}
