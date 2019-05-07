package net.finance.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.finance.entity.Budget;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Category;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

	@Query("select bc.category from BudgetCategories bc where bc.budget.id = :budgetId")
	List<Category> getCategoriesByBudget(Integer budgetId);

	@Query("select bp.budget, bp.idPeriod, bp.startDate, bp.endDate from BudgetPeriods bp where bp.budget.id = :budgetId order by bp.idPeriod")
	Optional<List<BudgetPeriods>> getPeriodsByBudget(Integer budgetId);

	@Query("select bp from BudgetPeriods bp where :now between bp.startDate and bp.endDate")
	Optional<BudgetPeriods> getPeriodsByDate(Date now);

}
