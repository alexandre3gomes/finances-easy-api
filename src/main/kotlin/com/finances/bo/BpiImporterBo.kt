package com.finances.bo

import com.finances.entity.Category
import com.finances.entity.Expense
import com.finances.repository.ExpenseRepository
import com.finances.repository.UserRepository
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.io.InputStream
import java.math.BigDecimal
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class BpiImporterBo(
    private val expenseRepository: ExpenseRepository,
    private val userRepository: UserRepository
) : ImporterBo {

    private val MOVEMENT_DATE_IDX = 0
    private val VALUE_DATE_IDX = 1
    private val DESCRIPTION_IDX = 2
    private val VALUE_IDX = 3

    override fun import(inputStream: InputStream): List<Expense> {
       return expenseRepository.saveAll(convertToExpenses(inputStream))
    }

    override fun convertToDate(dateValue: String): LocalDate? {
        return try {
            LocalDate.parse(dateValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        } catch (ex: DateTimeParseException) {
            null
        }
    }

    override fun convertToExpenses(inputStream: InputStream): List<Expense> {
        val auth = SecurityContextHolder.getContext().authentication ?: throw UserPrincipalNotFoundException(null)
        val user = userRepository.getUserByUsername(auth.name)
        val defaultCategory = Category(1, "Category Test", false)
        val sheet = WorkbookFactory.create(inputStream).getSheetAt(0)
        val listExpense = mutableListOf<Expense>()
        for (row in sheet) {
            for (cell in row) {
                if (cell.cellType == CellType.STRING && convertToDate(cell.stringCellValue) != null) {
                    val expireAt = workoutExpireAt(row)
                    val name = row.getCell(DESCRIPTION_IDX).stringCellValue
                    val value = row.getCell(VALUE_IDX).numericCellValue
                    if(value < 0) {
                        listExpense.add(
                            Expense(defaultCategory, user, name, BigDecimal.valueOf(value * -1), expireAt, "Imported automatically")
                        )
                    }
                    break
                }
            }
        }
        return listExpense
    }

    private fun workoutExpireAt(row: Row): LocalDateTime {
        var expireAt = convertLocalDataToLocalDateTime(convertToDate(row.getCell(MOVEMENT_DATE_IDX).stringCellValue)!!)
        if (convertToDate(row.getCell(VALUE_DATE_IDX).stringCellValue) != null) {
            expireAt = convertLocalDataToLocalDateTime(convertToDate(row.getCell(VALUE_DATE_IDX).stringCellValue)!!)
        }
        return expireAt
    }

    private fun convertLocalDataToLocalDateTime(localDate: LocalDate): LocalDateTime =
        localDate.atTime(
            LocalTime.now().hour,
            LocalTime.now().minute,
            LocalTime.now().second,
        )
}