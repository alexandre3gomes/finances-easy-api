package net.finance.bo;

import net.finance.dto.ExpenseFilterDTO;
import net.finance.repository.BudgetRepository;
import net.finance.repository.ExpenseRepository;
import net.finance.util.BuildMockDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ExpenseBoTests {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    ExpenseBo expenseBo;

    public void testListWithoutFilters(){
        Mockito.lenient().when(expenseRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(BuildMockDataUtil.buildPageOfExpenses());
        assertTrue("The size of returned list is different than ten", expenseBo.list(new ExpenseFilterDTO(), PageRequest.of(1, 10)).getTotalElements() == 10);
    }

    public void testListWithNameFilter(){
        Mockito.lenient().when(expenseRepository.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense());
        assertTrue("The size of returned list is different than ten",expenseBo.list(BuildMockDataUtil.buildExpenseFilterDTO(), PageRequest.of(1, 10)).getTotalElements() == 10);
    }

    public void testGetActualExpenseWithReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods());
        Mockito.lenient().when(expenseRepository.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense());
        assertTrue("The size of returned list is different than ten",expenseBo.getActualExpense().isPresent());
    }

    public void testGetActualExpenseWithoutReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.empty());
        Mockito.lenient().when(expenseRepository.findByExpireAtBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfExpense());
        assertFalse("The returned list isn't empty",expenseBo.getActualExpense().isPresent());
    }






}
