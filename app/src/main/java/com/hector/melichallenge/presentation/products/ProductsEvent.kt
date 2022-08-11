package com.hector.melichallenge.presentation.products

sealed class ProductsEvent {
    data class SearchProduct(val query: String, val offset: Int): ProductsEvent()
}