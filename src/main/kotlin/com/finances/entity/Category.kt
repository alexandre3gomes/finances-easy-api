package com.finances.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "category")
class Category(
    val name: String = "",
    val savings: Boolean = false
) {

    constructor(id: Int, name: String, savings: Boolean) : this(name, savings) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    val budgets: Set<BudgetCategories> = emptySet()
}
