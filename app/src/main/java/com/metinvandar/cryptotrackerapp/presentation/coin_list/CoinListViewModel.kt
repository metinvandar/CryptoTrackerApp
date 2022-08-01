package com.metinvandar.cryptotrackerapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            getCoins().collectLatest { resource ->
                when (resource) {
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
