package com.hector.melichallenge.domain.util

data class ErrorMessage (
    val message: String = "Cannot contact the server",
    val type: ErrorType = ErrorType.IO
) {

    enum class ErrorType{
        IO, HTTP
    }
}