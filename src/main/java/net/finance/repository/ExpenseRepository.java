package net.finance.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import net.finance.dto.CategoryGroupedValuesDTO;
import net.finance.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepositoryImplementation<Expense, Integer> {

	@Query("select new net.finance.dto.CategoryGroupedValuesDTO(cat, bp.startDate, bp.endDate, sum(coalesce(ex.value, 0)) as value) from BudgetPeriods bp"
			+ " left join Budget b on bp.budget = b.id" + " left join BudgetCategories bc on bc.budget = b.id"
			+ " left join Category cat on bc.category = cat.id"
			+ " left join Expense ex on cat.id = ex.category and ex.expireAt between bp.startDate and bp.endDate"
			+ " where b.id = :budgetId" + " group by cat, bp.startDate, bp.endDate" + " order by bp.startDate")
	List<CategoryGroupedValuesDTO> getValuesByCategories(Integer budgetId);

}
