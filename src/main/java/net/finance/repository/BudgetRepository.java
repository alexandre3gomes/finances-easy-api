package net.finance.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.finance.dto.report.PeriodValueDto;
import net.finance.entity.Budget;
import net.finance.entity.BudgetPeriods;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

	@Query("select bp.budget, bp.idPeriod, bp.startDate, bp.endDate from BudgetPeriods bp where bp.budget.id = :budgetId order by bp.idPeriod")
	Optional<List<BudgetPeriods>> getPeriodsByBudget(Integer budgetId);

	@Query("select bp from BudgetPeriods bp where :now between bp.startDate and bp.endDate")
	Optional<BudgetPeriods> getPeriodsByDate(Date now);

	@Query("select new net.finance.dto.report.PeriodValueDto(bp.startDate, bp.endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where b.id = :budgetId and cat.id = :categoryId"
			+ " group by bp.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.idPeriod")
	List<PeriodValueDto> getValuesByCategories(Integer budgetId, Integer categoryId);

	@Query("select new net.finance.dto.report.PeriodValueDto(bp.startDate, bp.endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where cat.id = :categoryId and :now between bp.startDate and bp.endDate "
			+ " group by bp.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.idPeriod")
	List<PeriodValueDto> getAggregateValueByDate(Date now, Integer categoryId);

}
