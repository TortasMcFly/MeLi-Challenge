package com.hector.melichallenge.data.remote.dto

data class Filter(
    val id: String,
    val name: String,
    val type: String,
    val values: List<ValueX>
)