package com.finances.bo;

import com.finances.entity.BudgetPeriods;
import com.finances.entity.Category;
import com.finances.repository.BudgetRepository;
import com.finances.repository.ExpenseRepository;
import com.finances.specifications.ExpensesSpec;
import lombok.NonNull;
import com.finances.dto.ExpenseFilterDTO;
import com.finances.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Transactional
public class ExpenseBo {

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
        Category category = expFilter.getCategoryId() != null ? Category.builder().id(expFilter.getCategoryId()).build() : null;
        return expenseRepository.findAll(
          where(ExpensesSpec.nameEquals(expFilter.getName())
            .and(ExpensesSpec.categoryEquals(category))
            .and(ExpensesSpec.userEquals(expFilter.getUser())
            .and(ExpensesSpec.expireAtBetween(expFilter.getStartDate(), expFilter.getEndDate())))),
          pageReq);
    }

    public Expense update(Expense dev) {
        return expenseRepository.save(dev);
    }

    public Optional<List<Expense>> getActualExpense() {
        Optional<BudgetPeriods> period = budgetRepository.getPeriodsByDate(LocalDateTime.now());
        if (period.isPresent()) {
            return expenseRepository.findByExpireAtBetween(period.get().getStartDate(), period.get().getEndDate());
        } else {
            return Optional.empty();
        }
    }

}
