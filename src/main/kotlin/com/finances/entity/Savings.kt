package com.finances.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "savings")
class Savings(
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    @ManyToOne
    val user: User,
    val description: String,
    val value: BigDecimal,
    val createdDate: LocalDateTime
) {

    constructor(id: Int, user: User, description: String, value: BigDecimal, createdDate: LocalDateTime) : this(
        user,
        description,
        value,
        createdDate
    ) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}
