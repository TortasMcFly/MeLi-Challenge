package com.hector.melichallenge.domain.use_case

import app.cash.turbine.test
import com.hector.melichallenge.domain.repository.FakeProductRepository
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetProductDetailUseCaseTest {

    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @Test
    fun `get product detail returns success result`() {
        runBlocking {

            // Given
            val itemId = "test-item-id"
            val repository: ProductRepository = FakeProductRepository(returnsError = false)
            getProductDetailUseCase = GetProductDetailUseCase(repository)

            // When
            val resultFlow = getProductDetailUseCase(itemId)

            // Then
            resultFlow.test {
                assertTrue( awaitItem() is Resource.Loading )
                assertTrue( awaitItem() is Resource.Success )
                awaitComplete()
            }
        }
    }

    @Test
    fun `get product detail returns error result`() {
        runBlocking {

            // Given
            val itemId = "test-item-id"
            val repository: ProductRepository = FakeProductRepository(returnsError = true)
            getProductDetailUseCase = GetProductDetailUseCase(repository)

            // When
            val resultFlow = getProductDetailUseCase(itemId)

            // Then
            resultFlow.test {
                assertTrue( awaitItem() is Resource.Loading )
                assertTrue( awaitItem() is Resource.Error )
                awaitComplete()
            }
        }
    }

}