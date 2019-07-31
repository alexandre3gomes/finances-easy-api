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

import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.NonNull;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.entity.QExpense;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;

@Service
@Transactional
public class ExpenseBo {

	@NonNull
	private final ExpenseRepository expenseRepository;
	private final BudgetRepository budgetRepository;
	final QExpense expense = new QExpense("expense");

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

	public Page<Expense> list(final ExpenseFilterDTO expFilter, final PageRequest pageReq) {
		BooleanExpression predicate = null;
		if (expFilter.getName() != null) {
			predicate = expense.name.containsIgnoreCase(expFilter.getName());
		}
		if (expFilter.getCategoryId() != null) {
			predicate.and(expense.category.eq(new Category(expFilter.getCategoryId())));
		}
		if (expFilter.getStartDate() != null && expFilter.getEndDate() != null) {
			predicate.and(expense.expireAt.between(expFilter.getStartDate(), expFilter.getEndDate()));
		}
		return expenseRepository.findAll(predicate, pageReq);
	}

	public Expense update(final Expense dev) {
		return expenseRepository.save(dev);
	}

	public Optional<List<Expense>> getActualExpense(final PageRequest pageReq) {
		final Date now = Calendar.getInstance().getTime();
		final Optional<BudgetPeriods> period = budgetRepository.getPeriodsByDate(now);
		final QExpense expense = new QExpense("expense");
		if (period.isPresent()) {
			final BooleanExpression betweenDate = expense.expireAt.between(period.get().getStartDate(),
					period.get().getEndDate());
			return Optional.of(expenseRepository.findAll(betweenDate, pageReq).getContent());
		} else {
			return Optional.empty();
		}
	}
}
