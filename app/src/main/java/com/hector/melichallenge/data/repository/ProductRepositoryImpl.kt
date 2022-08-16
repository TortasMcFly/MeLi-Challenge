package com.hector.melichallenge.data.repository

import com.hector.melichallenge.data.remote.MeLiApi
import com.hector.melichallenge.data.remote.dto.toDomain
import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.model.SearchDetail
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.util.ErrorMessage
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val api: MeLiApi
): ProductRepository {

    override suspend fun searchProduct(
        query: String,
        offset: Int
    ): Flow<Resource<SearchDetail>> = flow {

        emit(Resource.Loading())

        try {

            val queryResult = api.searchProduct(
                query,
                offset
            )
            
            emit(Resource.Success(queryResult.toDomain()))

        }
        catch (httpException: HttpException) {
            emit(Resource.Error(ErrorMessage(
                message = httpException.message(),
                type = ErrorMessage.ErrorType.HTTP
            )))
        }
        catch (ioException: IOException) {
            emit(Resource.Error(ErrorMessage(
                message = ioException.message ?: "Cannot contact the server",
                type = ErrorMessage.ErrorType.IO
            )))
        }

    }

    override suspend fun getProductDetail(
        itemId: String
    ): Flow<Resource<Product>> = flow {

        emit(Resource.Loading())

        try {

            val result = api.getProductDetail(itemId)
            val product = result[0]

            emit(Resource.Success(product.body.toDomain()))
        }
        catch (httpException: HttpException) {
            emit(Resource.Error(ErrorMessage(
                message = httpException.message(),
                type = ErrorMessage.ErrorType.HTTP
            )))
        }
        catch (ioException: IOException) {
            emit(Resource.Error(ErrorMessage(
                message = ioException.message ?: "Cannot contact the server",
                type = ErrorMessage.ErrorType.IO
            )))
        }
    }

}