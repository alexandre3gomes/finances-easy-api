package net.finance.bo;

import net.finance.repository.BudgetRepository;
import net.finance.repository.IncomeRepository;
import net.finance.util.BuildMockDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class IncomeBoTests {

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    IncomeBo incomeBo;

    public void testGetActualIncomeWithReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfBudgetPeriods());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        assertTrue(incomeBo.getActualIncome().isPresent());
    }

    public void testGetActualIncomeWithoutReturn(){
        Mockito.lenient().when(budgetRepository.getPeriodsByDate(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(Optional.empty());
        Mockito.lenient().when(incomeRepository.findByDateBetween(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(BuildMockDataUtil.buildOptionalOfIncomes());
        assertFalse(incomeBo.getActualIncome().isPresent());
    }
}
