package com.hector.melichallenge.domain.util

data class ValidationResult(
    var successful: Boolean = false,
    var errorMessage: String? = null
)
