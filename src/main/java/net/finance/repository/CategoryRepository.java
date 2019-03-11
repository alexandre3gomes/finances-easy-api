package net.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
