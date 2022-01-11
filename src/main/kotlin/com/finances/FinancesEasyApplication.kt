package com.finances

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinancesEasyApplication

fun main(args: Array<String>) {
    runApplication<FinancesEasyApplication>(*args)
}
