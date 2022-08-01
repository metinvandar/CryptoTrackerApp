package com.metinvandar.cryptotrackerapp.presentation.coin_history

import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory

sealed class HistoryUIState {
    data class Result(val coinHistory: List<CoinHistory>): HistoryUIState()
    object Empty: HistoryUIState()
}
