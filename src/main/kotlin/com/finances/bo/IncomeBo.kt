package com.finances.bo

import com.finances.dto.IncomeDTO
import com.finances.entity.BudgetPeriods
import com.finances.entity.Income
import com.finances.mapper.toDTO
import com.finances.repository.BudgetRepository
import com.finances.repository.IncomeRepository
import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
class IncomeBo(private val incomeRepository: IncomeRepository, private val budgetRepository: BudgetRepository) {

    fun create(income: Income): IncomeDTO {
        return incomeRepository.save(income).toDTO()
    }

    fun delete(id: Int) {
        incomeRepository.deleteById(id)
    }

    fun get(id: Int): IncomeDTO {
        return incomeRepository.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(pageReq: PageRequest): Page<IncomeDTO> {
        return incomeRepository.findAll(pageReq).map(Income::toDTO)
    }

    fun update(income: Income): IncomeDTO {
        return incomeRepository.save(income).toDTO()
    }

    fun actualIncome(): List<IncomeDTO> {
        val period: BudgetPeriods? = budgetRepository.getPeriodsByDate(LocalDateTime.now())
        if (period != null) {
            return incomeRepository.findByDateBetween(period.startDate, period.endDate).map(Income::toDTO)
        }
        return emptyList()
    }
}
