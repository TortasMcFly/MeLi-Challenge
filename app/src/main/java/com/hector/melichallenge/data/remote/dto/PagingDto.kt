package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Paging

data class PagingDto(
    val limit: Int,
    val offset: Int,
    val primary_results: Int,
    val total: Int
)

fun PagingDto.toDomain() = Paging (
    limit = limit,
    offset = offset,
    primaryResults = primary_results,
    total = total
)