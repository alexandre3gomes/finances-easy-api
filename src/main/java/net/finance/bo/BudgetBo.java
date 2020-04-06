package net.finance.bo;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class BudgetBo {

    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetBo(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public Budget create(Budget budget) {
        return budgetRepository.save(getNewBudget(budget));
    }

    public void delete(Integer id) {
        budgetRepository.deleteById(id);
    }

    public Budget get(Integer id) {
        return budgetRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Budget> list(PageRequest pageRequest) {
        return budgetRepository.findAll(pageRequest);
    }

    public Budget update(Budget budget) {
        budgetRepository.deleteById(budget.getId());
        return budgetRepository.save(getNewBudget(budget));
    }

    public List<CategoryAggregValuesDto> getActualBalance() {
        List<CategoryAggregValuesDto> ret = new ArrayList<>();
        List<Category> categories = categoryRepository.getCategoriesByPeriod(LocalDateTime.now());
        for (Category cat : categories) {
            CategoryAggregValuesDto catAg = new CategoryAggregValuesDto();
            catAg.setCategory(cat);
            catAg.setPeriodValue(budgetRepository.getAggregateValueByDate(LocalDateTime.now(), cat.getId()));
            ret.add(catAg);
        }
        return ret;
    }

    public Budget getNewBudget(Budget budget) {
        BudgetCategories[] budCat = budget.getCategories().stream().toArray(BudgetCategories[]::new);
        Budget newBudget = new Budget(budget.getUser(), budget.getStartDate(), budget.getEndDate(),
                budget.getBreakPeriod(), splitInMonths(budget), budCat);
        return newBudget;
    }

    private static Set<BudgetPeriods> splitInMonths(Budget budget) {
        Set<BudgetPeriods> periods = new TreeSet<>();
        LocalDateTime sDate = budget.getStartDate();
        LocalDateTime eDate = budget.getEndDate();
        int idPeriod = 0;
        while (sDate.isBefore(eDate)) {
            BudgetPeriods budPed = new BudgetPeriods();
            budPed.setBudget(budget);
            budPed.setIdPeriod(idPeriod);
            budPed.setStartDate(sDate);
            LocalDateTime finalDate = null;
            if (budget.getBreakPeriod() == BreakpointEnum.MONTHLY.getId()) {
                finalDate = sDate.plusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
                sDate = finalDate.plusDays(1).withHour(0).withMinute(0)
                        .withSecond(0);
            } else {
                sDate = sDate.withHour(0).withMinute(0).withSecond(0).plusWeeks(1);
                finalDate = sDate.withHour(23).withMinute(59).withSecond(59).plusDays(-1);
            }
            budPed.setEndDate(finalDate);
            periods.add(budPed);
            idPeriod++;
        }
        return periods;
    }
}
