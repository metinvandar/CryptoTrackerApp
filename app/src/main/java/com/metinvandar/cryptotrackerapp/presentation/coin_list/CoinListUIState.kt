package com.metinvandar.cryptotrackerapp.presentation.coin_list

import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType
import com.metinvandar.cryptotrackerapp.presentation.state.ErrorState

sealed class CoinListUIState {
    data class Loading(val isLoading: Boolean): CoinListUIState()
    data class Result(val coins: List<CoinDomainModel>): CoinListUIState()
    data class Error(override val errorType: ErrorType): CoinListUIState(), ErrorState
}
