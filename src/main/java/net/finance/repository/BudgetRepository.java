package net.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}
