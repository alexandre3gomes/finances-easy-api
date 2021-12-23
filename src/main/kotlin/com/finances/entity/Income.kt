package com.finances.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "income")
class Income(
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    val user: User,
    val name: String,
    val value: BigDecimal,
    val date: LocalDateTime,
    val description: String? = null
) {

    constructor(id: Int, user: User, name: String, value: BigDecimal, date: LocalDateTime, description: String?) : this(
        user,
        name,
        value,
        date,
        description
    ) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}
