package com.hector.melichallenge.domain.use_case

import com.hector.melichallenge.domain.util.ValidationResult

class ValidateSearchQueryUseCase {

    operator fun invoke(
        query: String
    ): ValidationResult {
        if(query.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The query can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}