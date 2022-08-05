package com.finances.controller

import com.finances.bo.ImporterBo
import com.finances.dto.ExpenseDTO
import com.finances.entity.Expense
import com.finances.mapper.toDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/import")
class ImporterController(
    private val importerBo: ImporterBo
) {

    @PostMapping("/expense")
    fun expense(@RequestParam file: MultipartFile): List<ExpenseDTO> =
        importerBo.import(file.inputStream).map(Expense::toDTO)
}
