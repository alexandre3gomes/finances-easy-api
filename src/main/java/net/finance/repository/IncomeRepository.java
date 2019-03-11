package net.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
