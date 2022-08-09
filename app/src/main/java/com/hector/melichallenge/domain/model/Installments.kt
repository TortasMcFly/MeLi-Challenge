package com.hector.melichallenge.domain.model

data class Installments(
    var quantity: Int = 0,
    var amount: Double = 0.0
) {

    fun stringFormat(): String {
        return "en ${quantity}x $ $amount"
    }

}