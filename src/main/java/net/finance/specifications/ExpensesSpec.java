package net.finance.specifications;

import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ExpensesSpec {

    public static Specification<Expense> nameEquals(final String name) {
        return (expense, query, builder) -> name == null ? builder.conjunction() : builder.equal(expense.get("name"), name);
    }

    public static Specification<Expense> expireAtBetween(final LocalDateTime startDate, final LocalDateTime endDate) {
        return (expense, query, builder) -> startDate == null || endDate == null ? builder.conjunction() : builder.between(expense.get("expireAt"),
          startDate, endDate);
    }

    public static Specification<Expense> categoryEquals(final Category category) {
        return (expense, query, builder) -> category == null ? builder.conjunction() : builder.equal(expense.get("category"), category);
    }

    public static Specification<Expense> userEquals(final User user) {
        return (expense, query, builder) -> user == null ? builder.conjunction() : builder.equal(expense.get("user"), user);
    }

}
