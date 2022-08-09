package com.hector.melichallenge.data.remote

import com.hector.melichallenge.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MeLiApi {

    @GET("MLM/search")
    suspend fun searchProduct(
        @Query("q") query: String,
        @Query("offset") offset: Int = 0,
    ): SearchResultDto

}