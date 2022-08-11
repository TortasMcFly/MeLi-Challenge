package com.hector.melichallenge.domain.model

data class SearchDetail(
    var query: String = "",
    var products: List<Product> = emptyList(),
    var paging: Paging? = null
) {

    fun getTotal(): Int {
        return paging?.total ?: 0
    }
}