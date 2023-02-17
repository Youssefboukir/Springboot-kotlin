package com.shop.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@ControllerAdvice
class ExceptionControllerAdvice {

    // handle the IllegalStateException exception
    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ExceptionResponse> {
        val errorMessage = ExceptionResponse(
            Date(),
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        // return a response entity with the error message and a BAD_REQUEST status code
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    // handle the ProductNotFoundException exception
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductBotFoundException(ex: ProductNotFoundException): ResponseEntity<ExceptionResponse> {
        val errorMessage = ExceptionResponse(
            Date(),
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        // return a response entity with the error message and a NOT_FOUND status code
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}