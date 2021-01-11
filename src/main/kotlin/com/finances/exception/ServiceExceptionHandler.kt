package com.finances.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.http.HttpStatus
import java.lang.NullPointerException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.NoSuchElementException

@ControllerAdvice
class ServiceExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException::class)
    fun handleGenericError() {
        // JUST MAPPING HTTP STATUS
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstraintException() {
        // JUST MAPPING HTTP STATUS
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoContentException() {
        // JUST MAPPING HTTP STATUS
    }
}