package com.hector.melichallenge.presentation.product_detail

import com.hector.melichallenge.domain.model.Product

data class ProductDetailState(
    val product: Product? = null,
    val query: String = "",
    val loading: Boolean = false
)