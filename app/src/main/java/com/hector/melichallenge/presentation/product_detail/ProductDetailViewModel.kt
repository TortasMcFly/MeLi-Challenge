package com.hector.melichallenge.presentation.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hector.melichallenge.domain.use_case.GetProductDetailUseCase
import com.hector.melichallenge.domain.util.onError
import com.hector.melichallenge.domain.util.onLoading
import com.hector.melichallenge.domain.util.onSuccess
import com.hector.melichallenge.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val stateHandle: SavedStateHandle
): ViewModel(){

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val productId = stateHandle.get<String>(Constants.NAV_ARGUMENT_PRODUCT_ID) ?: ""
        val query = stateHandle.get<String>(Constants.NAV_ARGUMENT_QUERY_SEARCH) ?: ""
        val installments = stateHandle.get<String>(Constants.NAV_ARGUMENT_PRODUCT_INSTALLMENTS) ?: ""
        _state.value = state.value.copy(
            query = query,
            installments = installments
        )
        getProductDetail(productId)
    }

    private fun getProductDetail(productId: String) = viewModelScope.launch {

        getProductDetailUseCase(productId).onEach { result ->

           result
               .onLoading {
                   _state.value = state.value.copy(
                       loading = true
                   )
               }
               .onSuccess {
                   _state.value = state.value.copy(
                       loading = false,
                       product = it
                   )
               }
               .onError {
                   _state.value = state.value.copy(
                       loading = false
                   )
                   _eventFlow.emit(UIEvent.ShowError(it ?: "An error occurred"))
               }

        }.launchIn(this)

    }

    sealed class UIEvent {
        data class ShowError(val message: String): UIEvent()
    }

}