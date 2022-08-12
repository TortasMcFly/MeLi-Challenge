package com.hector.melichallenge.data.remote.dto

import com.hector.melichallenge.domain.model.ProductPicture

data class ProductPictureDto(
    val url: String
)

fun ProductPictureDto.toDomain() = ProductPicture(
    url = url
)