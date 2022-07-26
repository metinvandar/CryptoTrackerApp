package com.metinvandar.cryptotrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.domain.usecase.GetSimplePriceUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCase
import com.metinvandar.cryptotrackerapp.presentation.state.CoinListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoins: GetCoinsUseCase
) : ViewModel() {
    private val _coinListState = MutableStateFlow<CoinListUIState>(CoinListUIState.Loading(true))
    val coinListState = _coinListState.asStateFlow()


    init {
        getInitialCoins()
    }

    fun getInitialCoins() {
        viewModelScope.launch {
            getCoins().collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _coinListState.value = CoinListUIState.Loading(resource.status)
                    }
                    is Resource.Success -> {
                        _coinListState.value = CoinListUIState.Result(resource.data)
                    }
                    is Resource.Error -> {
                        _coinListState.value = CoinListUIState.Error(resource.error)
                    }
                }
            }
        }
    }
}
