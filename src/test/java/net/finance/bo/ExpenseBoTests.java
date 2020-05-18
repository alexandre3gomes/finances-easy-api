package net.finance.bo;

import com.querydsl.core.types.dsl.BooleanExpression;
import net.finance.dto.ExpenseFilterDTO;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;
import net.finance.util.BuildMockDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseBoTests {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    ExpenseBo expenseBo;

    @Test
    public void testListWithoutFilters(){
        Mockito.lenient().when(expenseRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(BuildMockDataUtil.buildPageOfExpenses());
        Assert.assertTrue("The size of returned list is different than ten", expenseBo.list(new ExpenseFilterDTO(), PageRequest.of(1, 10)).getTotalElements() == 10);
    }

    @Test
    public void testListWithNameFilter(){
        Mockito.lenient().when(expenseRepository.findAll(ArgumentMatchers.any(BooleanExpression.class), ArgumentMatchers.any(PageRequest.class))).thenReturn(BuildMockDataUtil.buildPageOfExpenses());
        Assert.assertTrue("The size of returned list is different than ten",expenseBo.list(BuildMockDataUtil.buildExpenseFilterDTO(), PageRequest.of(1, 10)).getTotalElements() == 10);
    }

    @Test
    public void testGetActualExpenseWithReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods());
        Mockito.lenient().when(expenseRepository.findAll(ArgumentMatchers.any(BooleanExpression.class), ArgumentMatchers.any(PageRequest.class))).thenReturn(BuildMockDataUtil.buildPageOfExpenses());
        Assert.assertTrue("The size of returned list is different than ten",expenseBo.getActualExpense(PageRequest.of(1, 10)).isPresent());
    }

    @Test
    public void testGetActualExpenseWithoutReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.empty());
        Mockito.lenient().when(expenseRepository.findAll(ArgumentMatchers.any(BooleanExpression.class), ArgumentMatchers.any(PageRequest.class))).thenReturn(BuildMockDataUtil.buildPageOfExpenses());
        Assert.assertFalse("The returned list isn't empty",expenseBo.getActualExpense(PageRequest.of(1, 10)).isPresent());
    }






}
