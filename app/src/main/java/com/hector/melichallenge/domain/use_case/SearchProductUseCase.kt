package com.hector.melichallenge.domain.use_case

import com.hector.melichallenge.domain.model.SearchDetail
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchProductUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        query: String,
        offset: Int
    ): Flow<Resource<SearchDetail>> {
        return repository.searchProduct(query, offset)
    }

}