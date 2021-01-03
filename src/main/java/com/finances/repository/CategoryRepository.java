package com.finances.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.finances.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select bc.category from BudgetCategories bc where bc.budget.id = :budgetId")
	List<Category> getCategoriesByBudget(Integer budgetId);

	@Query("select bc.category from BudgetCategories bc "
			+ " left join BudgetPeriods bp on bc.budget.id = bp.budget.id "
			+ " where :now between bp.startDate and bp.endDate")
	List<Category> getCategoriesByPeriod(LocalDateTime now);

}
