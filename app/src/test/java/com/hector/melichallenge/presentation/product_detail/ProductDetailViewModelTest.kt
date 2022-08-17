package com.hector.melichallenge.presentation.product_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.use_case.GetProductDetailUseCase
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
class ProductDetailViewModelTest {

    @RelaxedMockK
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase
    private lateinit var viewModel: ProductDetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testItemId = "test_item_id"

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
    fun `when viewModel is created at first time and calls the method getProductDetail`() {
        runBlocking {

            // Given
            val savedStateHandle = SavedStateHandle().apply {
                set(Constants.NAV_ARGUMENT_PRODUCT_ID, testItemId)
            }

            val product = Product(id = testItemId)
            coEvery { getProductDetailUseCase(testItemId) } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(product))
            }

            // When
            viewModel = ProductDetailViewModel(
                getProductDetailUseCase,
                savedStateHandle
            )


            // Then
            assertTrue( viewModel.state.value.product == product )

        }
    }

}