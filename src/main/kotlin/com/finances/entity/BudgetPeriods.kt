package com.finances.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "budget_periods")
class BudgetPeriods(
    @ManyToOne
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    var budget: Budget,
    @Column(name = "id_period", insertable = false, updatable = false)
    val idPeriod: Int,
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now()
) : Comparable<BudgetPeriods>, Serializable {

    @EmbeddedId
    lateinit var budgetPeriodsId: BudgetPeriodsId

    @PrePersist
    fun prePersist() {
        this.budgetPeriodsId = BudgetPeriodsId(budget.id, idPeriod)
    }

    override fun compareTo(other: BudgetPeriods): Int {
        return if (startDate.isAfter(other.startDate)) 1 else 0
    }

}

@Embeddable
data class BudgetPeriodsId(
    @Column(name = "budget_id")
    val budgetId: Int,
    @Column(name = "id_period")
    val idPeriod: Int
) : Serializable