package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Installments

data class InstallmentsDto(
    val amount: Double,
    val currency_id: String,
    val quantity: Int,
    val rate: Double
)

fun InstallmentsDto.toDomain() = Installments(
    amount = amount,
    quantity = quantity
)