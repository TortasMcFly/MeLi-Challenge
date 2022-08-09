package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Attribute

data class AttributeDto(
    val attribute_group_id: String,
    val attribute_group_name: String,
    val id: String,
    val name: String,
    val source: Long,
    val value_id: String,
    val value_name: String,
    val value_struct: ValueStruct,
    val values: List<ValueXX>
)

fun AttributeDto.toDomain() = Attribute(
    id = id,
    name = name,
    valueName = value_name
)