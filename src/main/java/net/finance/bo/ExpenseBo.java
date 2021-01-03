package net.finance.bo;

import lombok.NonNull;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static net.finance.specifications.ExpensesSpec.categoryEquals;
import static net.finance.specifications.ExpensesSpec.expireAtBetween;
import static net.finance.specifications.ExpensesSpec.nameEquals;
import static net.finance.specifications.ExpensesSpec.userEquals;
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
          where(nameEquals(expFilter.getName())
            .and(categoryEquals(category))
            .and(userEquals(expFilter.getUser())
            .and(expireAtBetween(expFilter.getStartDate(), expFilter.getEndDate())))),
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
