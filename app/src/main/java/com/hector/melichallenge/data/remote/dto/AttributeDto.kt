package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Attribute

data class AttributeDto(
    val id: String?,
    val name: String?,
    val value_name: String?,
)

fun AttributeDto.toDomain() = Attribute(
    id = id ?: "",
    name = name ?: "",
    valueName = value_name ?: ""
)