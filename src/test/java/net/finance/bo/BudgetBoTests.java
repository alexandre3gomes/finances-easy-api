package net.finance.bo;

import net.finance.dto.report.PeriodValueDto;
import net.finance.entity.*;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        Budget budget = createBudget(BreakpointEnum.MONTHLY);
        Budget createdBudget = budgetBo.getNewBudget(budget);
        Assert.assertTrue("Periods needs to have full month", createdBudget.getPeriods().stream()
                .allMatch((budPer) -> budPer.getStartDate().getMonth() == budPer.getEndDate().getMonth()));
    }

    @Test
    public void createBudgetWeeklyBreakpoint() {
        Budget budget = createBudget(BreakpointEnum.WEEKLY);
        Budget createdBudget = budgetBo.getNewBudget(budget);
        Assert.assertTrue("Periods needs to have a duration of one week", createdBudget.getPeriods().stream()
                .allMatch((budPer) -> budPer.getEndDate().minusWeeks(1).plusDays(1).withHour(0).withMinute(0).withSecond(0).compareTo(budPer.getStartDate()) == 0));
    }


    @Test
    public void testGetActualValues() {
        Mockito.when(catRepo.getCategoriesByPeriod(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(createCategories());
        Mockito.when(budgetRepo.getAggregateValueByDate(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.anyInt())).thenReturn(createPeriodValues());
        Assert.assertTrue("The sum of actual values is not 200", budgetBo.getActualBalance().stream().mapToDouble((per) -> per.getPeriodValue().get(0).getActualValue().doubleValue()).sum() == 200);
    }

    private static List<PeriodValueDto> createPeriodValues() {
        return Arrays.asList(PeriodValueDto.builder().startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
                .endDate(LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
                .actualValue(new BigDecimal(100)).plannedValue(new BigDecimal(150)).build());
    }

    private static List<Category> createCategories() {
        return Arrays.asList(Category.builder().id(1).name("Test").build(), Category.builder().id(2).name("Test 1").build());
    }

    private static Budget createBudget(BreakpointEnum breakpoint) {
        return Budget.builder().id(1).startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
                .breakPeriod(breakpoint.getId())
                .user(User.builder().id(1).name("Alexandre").username("alexandre").build())
                .categories(createBudgetCategories()).periods(createPeriods()).build();
    }

    private static Set<BudgetPeriods> createPeriods() {
        return new HashSet<>();
    }

    private static Set<BudgetCategories> createBudgetCategories() {
        BudgetCategories catBud = new BudgetCategories();
        catBud.setCategory(Category.builder().id(1).name("Teste").savings(false).build());
        catBud.setBudget(Budget.builder().id(1).build());
        catBud.setValue(new BigDecimal(100));
        return new HashSet<>(Arrays.asList(catBud));
    }
}
