package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Shipping

data class ShippingDto(
    val free_shipping: Boolean,
    val logistic_type: String,
    val mode: String,
    val store_pick_up: Boolean,
    val tags: List<String>
)

fun ShippingDto.toDomain() = Shipping(
    freeShipping = free_shipping,
    logisticType = logistic_type
)