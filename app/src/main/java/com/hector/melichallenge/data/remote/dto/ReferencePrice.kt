package com.hector.melichallenge.data.remote.dto

data class ReferencePrice(
    val amount: Int,
    val conditions: ConditionsX,
    val currency_id: String,
    val exchange_rate_context: String,
    val id: String,
    val last_updated: String,
    val tags: List<Any>,
    val type: String
)