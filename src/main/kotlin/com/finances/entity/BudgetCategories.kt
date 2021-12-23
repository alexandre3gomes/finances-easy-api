package com.finances.entity

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "budget_categories")
class BudgetCategories(
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    var category: Category,
    @ManyToOne
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    var budget: Budget,
    @EmbeddedId
    var budgetCategoriesId: BudgetCategoriesId = BudgetCategoriesId(category.id, budget.id),
    var value: BigDecimal = BigDecimal.ZERO,
) : Serializable {

    @PrePersist
    fun prePersist() {
        this.budgetCategoriesId = BudgetCategoriesId(category.id, budget.id)
    }
}

@Embeddable
class BudgetCategoriesId(
    @Column(name = "category_id")
    var categoryId: Int,
    @Column(name = "budget_id")
    var budgetId: Int
) : Serializable
