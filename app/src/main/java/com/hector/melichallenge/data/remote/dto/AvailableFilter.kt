package com.hector.melichallenge.data.remote.dto

data class AvailableFilter(
    val id: String,
    val name: String,
    val type: String,
    val values: List<Value>
)