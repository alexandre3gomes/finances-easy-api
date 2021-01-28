package com.finances.bo

import com.finances.dto.SavingsDTO
import com.finances.entity.Savings
import com.finances.mapper.toDTO
import com.finances.repository.SavingsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class SavingsBo(private val savingsRepository: SavingsRepository) {

    fun create(savings: Savings): SavingsDTO {
        return savingsRepository.save(savings).toDTO()
    }

    fun delete(id: Int) {
        savingsRepository.deleteById(id)
    }

    fun get(id: Int): SavingsDTO {
        return savingsRepository.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(pageReq: PageRequest): Page<SavingsDTO> {
        return savingsRepository.findAll(pageReq).map(Savings::toDTO)
    }

    fun update(dev: Savings): SavingsDTO {
        return savingsRepository.save(dev).toDTO()
    }

    val totalSavings: BigDecimal
        get() = savingsRepository.sumSavings()
}
