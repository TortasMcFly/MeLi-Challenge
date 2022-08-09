package com.hector.melichallenge.domain.repository

import com.hector.melichallenge.domain.model.SearchDetail
import com.hector.melichallenge.domain.util.Resource
import com.hector.melichallenge.domain.util.ValidationResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun searchProduct(
        query: String,
        offset: Int
    ): Flow<Resource<SearchDetail>>

}