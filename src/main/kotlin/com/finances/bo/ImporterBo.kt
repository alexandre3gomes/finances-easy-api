package com.finances.bo

import com.finances.entity.Expense
import java.io.InputStream
import java.time.LocalDate

interface ImporterBo {

    fun import(inputStream: InputStream): List<Expense>
    fun convertToDate(dateValue: String): LocalDate?
    fun convertToExpenses(inputStream: InputStream): List<Expense>
}
