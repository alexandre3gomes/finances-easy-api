package com.finances.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "budget")
data class Budget(
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    @ManyToOne
    var user: User,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var breakperiod: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @OneToMany(mappedBy = "budget", cascade = [CascadeType.ALL])
    @OrderBy("idPeriod")
    var periods: Set<BudgetPeriods> = setOf()

    @OneToMany(mappedBy = "budget", cascade = [CascadeType.ALL])
    var categories: Set<BudgetCategories> = setOf()

    override fun toString(): String {
        return "user:$user, startDate: $startDate, endDate: $endDate, breakperiod: $breakperiod"
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}