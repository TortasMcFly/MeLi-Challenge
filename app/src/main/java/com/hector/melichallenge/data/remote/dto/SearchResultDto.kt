package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.SearchDetail

data class SearchResultDto(
    val available_filters: List<AvailableFilter>,
    val available_sorts: List<AvailableSort>,
    val country_default_time_zone: String,
    val filters: List<Filter>,
    val paging: PagingDto,
    val query: String,
    val results: List<ResultDto>,
    val site_id: String,
    val sort: Sort
)


fun SearchResultDto.toDomain() = SearchDetail(
    query = query,
    paging = paging.toDomain(),
    products = results.map { it.toDomain() }
)