package com.hector.melichallenge.data.remote.dto

data class ConditionsX(
    val context_restrictions: List<String>,
    val eligible: Boolean,
    val end_time: String,
    val start_time: String
)