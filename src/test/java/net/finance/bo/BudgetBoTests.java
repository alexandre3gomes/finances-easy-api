package net.finance.bo;

import net.finance.entity.Budget;
import net.finance.enums.BreakpointEnum;
import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import net.finance.util.BuildMockDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BudgetBoTests {

    @Mock
    private BudgetRepository budgetRepo;

    @Mock
    private CategoryRepository catRepo;

    @InjectMocks
    private BudgetBo budgetBo;

    public void createBudgetMonthlyBreakpoint() {
        Budget budget = BuildMockDataUtil.buildBudget(BreakpointEnum.MONTHLY);
        Budget createdBudget = budgetBo.getNewBudget(budget);
        assertTrue("Periods needs to have full month", createdBudget.getPeriods().stream()
                .allMatch((budPer) -> budPer.getStartDate().getMonth() == budPer.getEndDate().getMonth()));
    }

    public void createBudgetWeeklyBreakpoint() {
        Budget budget = BuildMockDataUtil.buildBudget(BreakpointEnum.WEEKLY);
        Budget createdBudget = budgetBo.getNewBudget(budget);
        assertTrue("Periods needs to have a duration of one week", createdBudget.getPeriods().stream()
                .allMatch((budPer) -> budPer.getEndDate().minusWeeks(1).plusDays(1).withHour(0).withMinute(0).withSecond(0).compareTo(budPer.getStartDate()) == 0));
    }


    public void testGetActualValues() {
        Mockito.when(catRepo.getCategoriesByPeriod(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildCategories());
        Mockito.when(budgetRepo.getAggregateValueByDate(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues());
        assertTrue("The sum of actual values is not 200", budgetBo.getActualBalance().stream().mapToDouble((per) -> per.getPeriodValue().get(0).getActualValue().doubleValue()).sum() == 200);
    }

}
