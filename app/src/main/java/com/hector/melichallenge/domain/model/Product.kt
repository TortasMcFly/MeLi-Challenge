package com.hector.melichallenge.domain.model

data class Product (
    var id: String = "",
    var title: String = "",
    var price: Double = 0.0,
    var currencyId: String = "",
    var condition: String = "",
    var thumbnail: String = "",
    var installments: Installments? = null,
    var shipping: Shipping? = null,
    var attributes: List<Attribute>? = null
) {

    fun getInstallments(): String? {
        return installments?.stringFormat()
    }

    fun getBrand(): String? {
        if(attributes == null) return null
        if(attributes!!.isEmpty()) return null

        return attributes!!.map {
            if(it.isBrand()) "Vendido por ${it.valueName}"
            else ""
        } [0]
    }

    fun isFullDelivery(): Boolean {
        return shipping?.isFullDelivery() ?: false
    }

    fun hasFreeShipping(): Boolean {
        return shipping?.freeShipping ?: false
    }
}