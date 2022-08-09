package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.Product

data class ResultDto(
    val accepts_mercadopago: Boolean,
    val address: Address,
    val attributes: List<AttributeDto>,
    val available_quantity: Int,
    val buying_mode: String,
    val catalog_listing: Boolean,
    val catalog_product_id: String,
    val category_id: String,
    val condition: String,
    val currency_id: String,
    val discounts: Any,
    val domain_id: String,
    val id: String,
    val installments: InstallmentsDto,
    val listing_type_id: String,
    val match_score: Any,
    val melicoin: Any,
    val offer_score: Any,
    val offer_share: Any,
    val official_store_id: Int,
    val order_backend: Int,
    val original_price: Any,
    val permalink: String,
    val price: Double,
    val prices: Prices,
    val sale_price: Any,
    val seller: Seller,
    val seller_address: SellerAddress,
    val shipping: ShippingDto,
    val site_id: String,
    val sold_quantity: Int,
    val stop_time: String,
    val tags: List<String>,
    val thumbnail: String,
    val thumbnail_id: String,
    val title: String,
    val use_thumbnail_id: Boolean,
    val winner_item_id: Any
)

fun ResultDto.toDomain() = Product(
    id = id,
    title = title,
    price = price,
    currencyId = currency_id,
    condition = condition,
    thumbnail = thumbnail,
    installments = installments.toDomain(),
    shipping = shipping.toDomain(),
    attributes = attributes.map { it.toDomain() }
)