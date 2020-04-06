package net.finance.bo;

import net.finance.entity.*;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BudgetBoTests {

    @Mock
    private BudgetRepository budgetRepo;

    @Mock
    private CategoryRepository catRepo;

    @InjectMocks
    private BudgetBo budgetBo;

    @Test
    public void createBudgetMonthlyBreakpoint() {
        Budget budget = createBudget();
        Budget createdBudget = budgetBo.getNewBudget(budget);
        Assert.assertTrue("Periods needs to have full month", createdBudget.getPeriods().stream()
                .allMatch((budPer) -> budPer.getStartDate().getMonth() == budPer.getEndDate().getMonth()));
    }

    private static Budget createBudget() {
        return Budget.builder().id(1).startDate(LocalDateTime.of(2020, 01, 01, 00, 00))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .breakPeriod(BreakpointEnum.MONTHLY.getId())
                .user(User.builder().id(1).name("Alexandre").username("alexandre").build())
                .categories(createCategories()).periods(createPeriods()).build();
    }

    private static Set<BudgetPeriods> createPeriods() {
        return new HashSet<>();
    }

    private static Set<BudgetCategories> createCategories() {
        BudgetCategories catBud = new BudgetCategories();
        catBud.setCategory(Category.builder().id(1).name("Teste").savings(false).build());
        catBud.setBudget(Budget.builder().id(1).build());
        catBud.setValue(new BigDecimal(100));
        return new HashSet<>(Arrays.asList(catBud));
    }
}
