package com.finances.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.finances.entity.BudgetPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finances.dto.report.PeriodValueDto;
import com.finances.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

	@Query("select new com.finances.entity.BudgetPeriods(bp.budget, bp.idPeriod, bp.startDate, bp.endDate) from BudgetPeriods bp where bp.budget.id = :budgetId order by bp.idPeriod")
	List<BudgetPeriods> getPeriodsByBudget(Integer budgetId);

	@Query("select bp from BudgetPeriods bp where :now between bp.startDate and bp.endDate")
	Optional<BudgetPeriods> getPeriodsByDate(LocalDateTime now);

	@Query("select new com.finances.dto.report.PeriodValueDto(bp.startDate, bp.endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where b.id = :budgetId and cat.id = :categoryId"
			+ " group by bp.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.idPeriod")
	List<PeriodValueDto> getValuesByCategories(Integer budgetId, Integer categoryId);

	@Query("select new com.finances.dto.report.PeriodValueDto(bp.startDate, bp.endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where cat.id = :categoryId and :now between bp.startDate and bp.endDate "
			+ " group by bp.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.idPeriod")
	List<PeriodValueDto> getAggregateValueByDate(LocalDateTime now, Integer categoryId);

}
