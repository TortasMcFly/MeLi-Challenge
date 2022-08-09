package com.hector.melichallenge.domain.model

data class Paging(
    var total: Int = 0,
    var primaryResults: Int = 0,
    var offset: Int = 0,
    var limit: Int = 0
)