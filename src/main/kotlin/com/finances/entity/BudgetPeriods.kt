package com.finances.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "budget_periods")
data class BudgetPeriods(
    @Id
    @ManyToOne
    var budget: Budget,
    @Id
    var idPeriod: Int = 0,
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now()
) : Comparable<BudgetPeriods>, Serializable {

    override fun compareTo(other: BudgetPeriods): Int {
        return if (startDate.isAfter(other.startDate)) 1 else 0
    }
}
