package net.finance.bo;

import net.finance.repository.BudgetRepository;
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
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class IncomeBoTests {

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    IncomeBo incomeBo;

    @Test
    public void testGetActualIncomeWithReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        Assert.assertTrue(incomeBo.getActualIncome().isPresent());
    }

    @Test
    public void testGetActualIncomeWithoutReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.empty());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        Assert.assertFalse(incomeBo.getActualIncome().isPresent());
    }
}
