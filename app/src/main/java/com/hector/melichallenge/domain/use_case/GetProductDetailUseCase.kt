package com.hector.melichallenge.domain.use_case

import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase (
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        itemId: String
    ): Flow<Resource<Product>> {
        return repository.getProductDetail(itemId)
    }

}