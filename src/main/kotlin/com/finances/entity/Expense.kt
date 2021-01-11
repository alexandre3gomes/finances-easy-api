package com.finances.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "expense")
data class Expense(
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne(optional = false)
    val category: Category? = null,
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    val user: User,
    val name: String,
    val value: BigDecimal,
    val expireAt: LocalDateTime,
    val description: String? = null
) {

    constructor(id: Int, category: Category?, user: User, name: String, value: BigDecimal, expireAt: LocalDateTime, description: String?) : this(
        category, user, name, value, expireAt, description
    ) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}