package com.hector.melichallenge.domain.repository

import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.model.SearchDetail
import com.hector.melichallenge.domain.util.ErrorMessage
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository(
    private val returnsError: Boolean
): ProductRepository {

    override suspend fun searchProduct(query: String, offset: Int): Flow<Resource<SearchDetail>> = flow {
        emit(Resource.Loading())

        if(returnsError) {
            emit(Resource.Error(ErrorMessage()))
            return@flow
        }
        emit(Resource.Success(SearchDetail()))
    }

    override suspend fun getProductDetail(itemId: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())

        if(returnsError) {
            emit(Resource.Error(ErrorMessage()))
            return@flow
        }
        emit(Resource.Success(Product(id = itemId, title = "test product name")))
    }

}