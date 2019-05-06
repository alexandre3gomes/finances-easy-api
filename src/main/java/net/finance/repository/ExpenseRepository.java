package net.finance.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import net.finance.dto.report.PeriodValueDto;
import net.finance.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

	@Query("select new net.finance.dto.report.PeriodValueDto(bp.startDate, bp.endDate, bc.value as plannedValue, sum(coalesce(ex.value, 0)) as actualValue) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where b.id = :budgetId and cat.id = :categoryId"
			+ " group by bp.idPeriod, bp.startDate, bp.endDate, bc.value" + " order by bp.idPeriod")
	List<PeriodValueDto> getValuesByCategories(Integer budgetId, Integer categoryId);

	List<Expense> findByExpireAtBetween(Date startDate, Date endDate);

}
