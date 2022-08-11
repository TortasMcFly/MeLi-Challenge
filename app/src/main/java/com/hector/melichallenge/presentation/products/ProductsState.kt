package com.hector.melichallenge.presentation.products

import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.model.SearchDetail

data class ProductsState(
    val loading: Boolean = false,
    val searchDetail: SearchDetail? = null,
    val products: List<Product>? = null,
    val query: String = ""
)