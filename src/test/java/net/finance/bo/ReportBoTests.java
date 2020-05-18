package net.finance.bo;

import net.finance.repository.BudgetRepository;
import net.finance.repository.CategoryRepository;
import net.finance.repository.IncomeRepository;
import net.finance.util.BuildMockDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class ReportBoTests {

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    BudgetRepository budgetRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ReportBo reportBo;

    @Test
    public void testByCategoryWithReturn(){
        Mockito.lenient().when(categoryRepository.getCategoriesByBudget(ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildCategories());
        Mockito.lenient().when(budgetRepository.getValuesByCategories(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues());
        Assert.assertTrue("The size of returned list is different than two",reportBo.byCategory(1).size() == 2);
    }

    @Test
    public void testByCategoryWithoutReturn(){
        Mockito.lenient().when(categoryRepository.getCategoriesByBudget(ArgumentMatchers.anyInt())).thenReturn(new ArrayList<>());
        Mockito.lenient().when(budgetRepository.getValuesByCategories(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildPeriodValues());
        Assert.assertTrue("The returned list isn't empty",reportBo.byCategory(1).isEmpty());
    }

    @Test
    public void testIncomeByPeriodWithReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByBudget(ArgumentMatchers.anyInt())).thenReturn(BuildMockDataUtil.buildListOfBudgetPeriods());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        Assert.assertTrue("The size of returned list is different than 2", reportBo.incomeByPeriod(1).size() == 2);

    }

    @Test
    public void testIncomeByPeriodWithoutReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByBudget(ArgumentMatchers.anyInt())).thenReturn(new ArrayList<>());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        Assert.assertTrue("The size of returned list is different than 2", reportBo.incomeByPeriod(1).isEmpty());
    }

}
