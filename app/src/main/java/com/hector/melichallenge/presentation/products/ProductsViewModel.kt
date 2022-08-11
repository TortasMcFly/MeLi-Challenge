package com.hector.melichallenge.presentation.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.domain.use_case.SearchProductUseCase
import com.hector.melichallenge.domain.util.onError
import com.hector.melichallenge.domain.util.onLoading
import com.hector.melichallenge.domain.util.onSuccess
import com.hector.melichallenge.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase,
    private val stateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val query = stateHandle.get<String>(Constants.NAV_ARGUMENT_QUERY_SEARCH) ?: ""
        _state.value = state.value.copy(
            query = query
        )
        onEvent(ProductsEvent.SearchProduct(query, 0))
    }

    private fun onEvent(event: ProductsEvent) {
        when(event) {
            is ProductsEvent.SearchProduct -> {
                searchProduct(event.query, event.offset)
            }
        }
    }

    private fun searchProduct(query: String, offset: Int) = viewModelScope.launch {
        searchProductUseCase(query, offset).onEach { result ->
            result
                .onLoading {
                    _state.value = state.value.copy(
                        loading = true
                    )
                }
                .onSuccess {
                    _state.value = state.value.copy(
                        loading = false,
                        searchDetail = it,
                        products = it?.products
                    )
                }
                .onError {
                    _state.value = state.value.copy(
                        loading = false
                    )
                    _eventFlow.emit(
                        UIEvent.ShowError(it ?: "An error occurred")
                    )
                }
        }.launchIn(this)

    }

    sealed class UIEvent {
        data class ShowError(val message: String): UIEvent()
    }

}