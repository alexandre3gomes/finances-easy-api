package com.finances.entity

import javax.persistence.*

@Entity
@Table(name = "category")
data class Category(
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
    val budgets: Set<BudgetCategories> = emptySet()

}