package com.hector.melichallenge.domain.use_case

import app.cash.turbine.test
import com.hector.melichallenge.domain.repository.FakeProductRepository
import com.hector.melichallenge.domain.repository.ProductRepository
import com.hector.melichallenge.domain.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SearchProductUseCaseTest {

    private lateinit var searchProductUseCase: SearchProductUseCase

    @Test
    fun `search product returns success result`() {
        runBlocking {

            // Given
            val query = "test query"
            val repository: ProductRepository = FakeProductRepository(returnsError = false)
            searchProductUseCase = SearchProductUseCase(repository)

            // When
            val resultFlow = searchProductUseCase(query, 0)

            // Then
            resultFlow.test {
                assertTrue( awaitItem() is Resource.Loading )
                assertTrue( awaitItem() is Resource.Success )
                awaitComplete()
            }
        }
    }

    @Test
    fun `search product returns error result`() {
        runBlocking {

            // Given
            val query = "test query"
            val repository: ProductRepository = FakeProductRepository(returnsError = true)
            searchProductUseCase = SearchProductUseCase(repository)

            // When
            val resultFlow = searchProductUseCase(query, 0)

            // Then
            resultFlow.test {
                assertTrue( awaitItem() is Resource.Loading )
                assertTrue( awaitItem() is Resource.Error )
                awaitComplete()
            }
        }
    }

}