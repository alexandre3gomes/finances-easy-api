package net.finance.bo;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.entity.QExpense;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ExpenseBo {

    private final QExpense expense = new QExpense("expense");
    @NonNull
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public ExpenseBo(ExpenseRepository expenseRep, BudgetRepository budgetRepository) {
        expenseRepository = expenseRep;
        this.budgetRepository = budgetRepository;
    }

    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void delete(Integer id) {
        expenseRepository.deleteById(id);
    }

    public Expense get(Integer id) {
        return expenseRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Expense> list(ExpenseFilterDTO expFilter, PageRequest pageReq) {
        BooleanExpression predicate = null;
        if (expFilter.getName() != null) predicate = expense.name.containsIgnoreCase(expFilter.getName());
        if (expFilter.getCategoryId() != null) if (predicate != null)
            predicate = predicate.and(expense.category.eq(new Category(expFilter.getCategoryId())));
        else
            predicate = expense.category.eq(new Category(expFilter.getCategoryId()));
        if (expFilter.getStartDate() != null && expFilter.getEndDate() != null) if (predicate != null)
            predicate = predicate.and(expense.expireAt.between(expFilter.getStartDate(), expFilter.getEndDate()));
        else
            predicate = expense.expireAt.between(expFilter.getStartDate(), expFilter.getEndDate());
        if (expFilter.getUser() != null)
            if (predicate != null) predicate = predicate.and(expense.user.eq(expFilter.getUser()));
            else
                predicate = expense.user.eq(expFilter.getUser());
        if (predicate != null) return expenseRepository.findAll(predicate, pageReq);
        else
            return expenseRepository.findAll(pageReq);
    }

    public Expense update(Expense dev) {
        return expenseRepository.save(dev);
    }

    public Optional<List<Expense>> getActualExpense(PageRequest pageReq) {
        Date now = Calendar.getInstance().getTime();
        Optional<BudgetPeriods> period = budgetRepository.getPeriodsByDate(now);
        QExpense expense = new QExpense("expense");
        if (period.isPresent()) {
            BooleanExpression betweenDate = expense.expireAt.between(period.get().getStartDate(),
                    period.get().getEndDate());
            return Optional.of(expenseRepository.findAll(betweenDate, pageReq).getContent());
        } else return Optional.empty();
    }
}
