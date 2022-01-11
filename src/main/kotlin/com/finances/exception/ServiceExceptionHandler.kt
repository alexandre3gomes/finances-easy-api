package com.finances.exception

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ServiceExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException::class)
    @ResponseBody
    fun handleGenericError(ex: NullPointerException) = ex.message

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseBody
    fun handleConstraintException(ex: DataIntegrityViolationException) = ex.message

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoSuchElementException::class)
    @ResponseBody
    fun handleNoContentException(ex: NoSuchElementException) = ex.message
}
