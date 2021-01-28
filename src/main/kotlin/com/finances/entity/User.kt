package com.finances.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "app_users")
data class User(
    val name: String = "",
    var username: String = ""
) {

    constructor(id: Int, name: String, username: String) : this(name, username) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Transient
    var token: String? = null

    @OneToMany(mappedBy = "user")
    @Transient
    private val budgets: List<Budget> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val expenses: List<Expense> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val incomes: List<Income> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val savings: List<Savings> = mutableListOf()
}
