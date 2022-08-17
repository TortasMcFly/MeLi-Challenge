package com.hector.melichallenge.presentation.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hector.melichallenge.domain.model.SearchDetail
import com.hector.melichallenge.domain.use_case.SearchProductUseCase
import com.hector.melichallenge.domain.util.Resource
import com.hector.melichallenge.presentation.utils.Constants
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @RelaxedMockK
    private lateinit var searchProductUseCase: SearchProductUseCase
    private lateinit var viewModel: ProductsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testQuery = "test_query"

    @Before
    @Throws(Exception::class)
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is created at first time and calls the event Search`() {
        runBlocking {

            // Given
            val savedStateHandle = SavedStateHandle().apply {
                set(Constants.NAV_ARGUMENT_QUERY_SEARCH, testQuery)
            }

            val searchDetail = SearchDetail(query = testQuery)
            coEvery { searchProductUseCase(testQuery, 0) } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(searchDetail))
            }

            // When
            viewModel = ProductsViewModel(
                searchProductUseCase,
                savedStateHandle
            )


            // Then
            assertTrue( viewModel.state.value.searchDetail == searchDetail )

        }
    }

}