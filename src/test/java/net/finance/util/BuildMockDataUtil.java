package net.finance.util;

import net.finance.dto.ExpenseFilterDTO;
import net.finance.dto.report.PeriodValueDto;
import net.finance.entity.Budget;
import net.finance.entity.BudgetCategories;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.entity.Income;
import net.finance.entity.User;
import net.finance.enums.BreakpointEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BuildMockDataUtil {

    public static Page<Expense> buildPageOfExpenses() {
        List<Expense> expenses = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            expenses.add(x, Expense.builder().id(x + 1).name("Name test " + x + 1)
              .description("Description test " + x + 1)
              .user(User.builder().name("Test User").username("test").build())
              .category(Category.builder().id(x + 1).name("Category " + x + 1).build())
              .expireAt(LocalDateTime.now()).build());
        }
        return new PageImpl<>(expenses);
    }

    public static List<PeriodValueDto> buildPeriodValues() {
        return Arrays.asList(PeriodValueDto.builder().startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
          .endDate(LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
          .actualValue(new BigDecimal(100)).plannedValue(new BigDecimal(150)).build());
    }

    public static List<Category> buildCategories() {
        return Arrays.asList(Category.builder().id(1).name("Test").build(), Category.builder().id(2).name("Test 1").build());
    }

    public static Optional<BudgetPeriods> buildOptionalOfBudgetPeriods() {
        return Optional.of(BudgetPeriods.builder().idPeriod(0)
          .startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
          .endDate(LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
          .budget(buildBudget(BreakpointEnum.MONTHLY)).build());
    }

    public static Budget buildBudget(BreakpointEnum breakpoint) {
        return Budget.builder().id(1).startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
          .endDate(LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
          .breakPeriod(breakpoint.getId())
          .user(User.builder().id(1).name("Alexandre").username("alexandre").build())
          .categories(BuildMockDataUtil.buildSetOfBudgetCategories()).periods(buildSetOfBudgetPeriods()).build();
    }

    public static Set<BudgetCategories> buildSetOfBudgetCategories() {
        BudgetCategories catBud = new BudgetCategories();
        catBud.setCategory(Category.builder().id(1).name("Teste").savings(false).build());
        catBud.setBudget(Budget.builder().id(1).build());
        catBud.setValue(new BigDecimal(100));
        return new HashSet<>(Arrays.asList(catBud));
    }

    public static Set<BudgetPeriods> buildSetOfBudgetPeriods() {
        return new HashSet<>();
    }

    public static ExpenseFilterDTO buildExpenseFilterDTO() {
        ExpenseFilterDTO filter = new ExpenseFilterDTO();
        filter.setName("Name test 1");
        filter.setCategoryId(1);
        filter.setStartDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME));
        filter.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME));
        filter.setUser(User.builder().name("Test User").username("test").build());
        return filter;
    }

    public static Optional<List<Income>> buildOptionalOfIncomes() {
        return Optional.of(Arrays.asList(Income.builder().id(1).name("Income test").description("Income test")
          .date(LocalDateTime.now()).user(buildUser()).value(new BigDecimal(100)).build()));
    }

    public static User buildUser() {
        return User.builder()
          .name("Test User")
          .username("test").build();
    }

    public static List<BudgetPeriods> buildListOfBudgetPeriods() {
        return Arrays.asList(BudgetPeriods.builder().idPeriod(0)
            .startDate(LocalDateTime.parse("2020-01-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
            .endDate(LocalDateTime.parse("2020-01-31T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
            .budget(buildBudget(BreakpointEnum.MONTHLY)).build(),
          BudgetPeriods.builder().idPeriod(0)
            .startDate(LocalDateTime.parse("2020-02-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME))
            .endDate(LocalDateTime.parse("2020-02-28T23:59:59", DateTimeFormatter.ISO_DATE_TIME))
            .budget(buildBudget(BreakpointEnum.MONTHLY)).build());
    }

    public static Optional<List<Expense>> buildOptionalOfExpense() {
        return Optional.of(Arrays.asList(Expense.builder().id(1).name("Expense test").description("Expense test")
          .category(Category.builder().id(1).name("Category test").build()).build()));
    }

}
