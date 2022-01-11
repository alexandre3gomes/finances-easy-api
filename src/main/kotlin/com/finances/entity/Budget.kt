package com.finances.entity

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.Table

@Entity
@Table(name = "budget")
class Budget(
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

    @OneToMany(mappedBy = "budget", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderBy("idPeriod")
    val periods: MutableSet<BudgetPeriods> = mutableSetOf()

    @OneToMany(mappedBy = "budget", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val categories: MutableSet<BudgetCategories> = mutableSetOf()

    fun addPeriod(period: BudgetPeriods) {
        periods.add(period)
    }

    fun addCategory(category: BudgetCategories) {
        categories.add(category)
    }

}
