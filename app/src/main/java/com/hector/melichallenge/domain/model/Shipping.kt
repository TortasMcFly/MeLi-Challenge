package com.hector.melichallenge.domain.model

data class Shipping(
    var freeShipping: Boolean = false,
    var logisticType: String = ""
) {

    fun isFullDelivery(): Boolean {
        return logisticType == FULL
    }

    companion object {
        const val FULL = "fulfillment"
    }
}