package net.finance.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.finance.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select bc.category from BudgetCategories bc where bc.budget.id = :budgetId")
	List<Category> getCategoriesByBudget(Integer budgetId);

	@Query("select bc.category from BudgetCategories bc "
			+ " left join BudgetPeriods bp on bc.budget.id = bp.budget.id "
			+ " where :now between bp.startDate and bp.endDate")
	List<Category> getCategoriesByPeriod(LocalDateTime now);

}
