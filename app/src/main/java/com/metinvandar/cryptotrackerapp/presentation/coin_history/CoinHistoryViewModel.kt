package com.metinvandar.cryptotrackerapp.presentation.coin_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinHistoryViewModel @Inject constructor(private val getCoinHistory: GetCoinHistoryUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUIState>(HistoryUIState.Empty)
    val uiState = _uiState.asStateFlow()

    fun getHistory(coinId: String) {
        viewModelScope.launch {
            getCoinHistory(coinId).collectLatest { coinHistory ->
                if (!coinHistory.isNullOrEmpty()) {
                    _uiState.value = HistoryUIState.Result(coinHistory)
                } else {
                    _uiState.value = HistoryUIState.Empty
                }
            }
        }
    }
}