package net.finance.bo;

import net.finance.dto.report.CategoryAggregValuesDto;
import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static Set<BudgetPeriods> splitInMonths(Budget budget) {
        Set<BudgetPeriods> periods = new TreeSet<>();
        LocalDateTime sDate = new LocalDateTime(budget.getStartDate());
        LocalDateTime eDate = new LocalDateTime(budget.getEndDate());
        int idPeriod = 0;
        while (sDate.isBefore(eDate)) {
            BudgetPeriods budPed = new BudgetPeriods();
            budPed.setBudget(budget);
            budPed.setIdPeriod(idPeriod);
            budPed.setStartDate(sDate.toDate());
            Date finalDate = null;
            if (budget.getBreakPeriod() == BreakpointEnum.MONTHLY.getId()) {
                finalDate = sDate.plusDays(-1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
                        .plusMonths(1).toDate();
                sDate = new LocalDateTime(finalDate).plusDays(1).withHourOfDay(0).withMinuteOfHour(0)
                        .withSecondOfMinute(0);
            } else {
                sDate = sDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).plusWeeks(1);
                finalDate = sDate.hourOfDay().withMaximumValue().minuteOfHour().withMaximumValue().secondOfMinute()
                        .withMaximumValue().plusDays(-1).toDate();
            }
            budPed.setEndDate(finalDate);
            periods.add(budPed);
            idPeriod++;
        }
        return periods;
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
        Date now = Calendar.getInstance().getTime();
        List<Category> categories = categoryRepository.getCategoriesByPeriod(now);
        for (Category cat : categories) {
            CategoryAggregValuesDto catAg = new CategoryAggregValuesDto();
            catAg.setCategory(cat);
            catAg.setPeriodValue(budgetRepository.getAggregateValueByDate(now, cat.getId()));
            ret.add(catAg);
        }
        return ret;
    }

    private Budget getNewBudget(Budget budget) {
        BudgetCategories[] budCat = budget.getCategories().stream().toArray(BudgetCategories[]::new);
        Budget newBudget = new Budget(budget.getUser(), budget.getStartDate(), budget.getEndDate(),
                budget.getBreakPeriod(), splitInMonths(budget), budCat);
        return newBudget;
    }
}
