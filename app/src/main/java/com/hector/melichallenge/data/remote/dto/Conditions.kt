package com.hector.melichallenge.data.remote.dto

data class Conditions(
    val context_restrictions: List<String>,
    val eligible: Boolean,
    val end_time: Any,
    val start_time: Any
)