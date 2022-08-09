package com.hector.melichallenge.domain.model

data class Attribute(
    var id: String = "",
    var name: String = "",
    var valueName: String = ""
) {

    fun isBrand(): Boolean {
        return id == BRAND_ID
    }

    companion object {
        const val BRAND_ID = "BRAND"
    }
}