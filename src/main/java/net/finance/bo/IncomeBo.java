package net.finance.bo;

import lombok.NonNull;
import net.finance.entity.BudgetPeriods;
import net.finance.entity.Income;
import net.finance.repository.BudgetRepository;
import net.finance.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class IncomeBo {

    @NonNull
    private final IncomeRepository incomeRepository;

    @NonNull
    private final BudgetRepository budgetRepository;

    @Autowired
    public IncomeBo(IncomeRepository incomeRep, BudgetRepository budgetRep) {
        incomeRepository = incomeRep;
        budgetRepository = budgetRep;
    }

    public Income create(Income income) {
        return incomeRepository.save(income);
    }

    public void delete(Integer id) {
        incomeRepository.deleteById(id);
    }

    public Income get(Integer id) {
        return incomeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Income> list(PageRequest pageReq) {
        return incomeRepository.findAll(pageReq);
    }

    public Income update(Income income) {
        return incomeRepository.save(income);
    }

    public Optional<List<Income>> getActualIncome() {
        Optional<BudgetPeriods> period = budgetRepository.getPeriodsByDate(LocalDateTime.now());
        if (period.isPresent())
			return incomeRepository.findByDateBetween(period.get().getStartDate(), period.get().getEndDate());
		else
			return Optional.empty();
    }

}
