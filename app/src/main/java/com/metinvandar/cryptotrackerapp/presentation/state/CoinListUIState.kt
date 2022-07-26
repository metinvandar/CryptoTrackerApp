package com.metinvandar.cryptotrackerapp.presentation.state

import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType

sealed class CoinListUIState {
    data class Loading(val isLoading: Boolean): CoinListUIState()
    data class Result(val coins: List<CoinDomainModel>): CoinListUIState()
    data class Error(override val errorType: ErrorType): CoinListUIState(), ErrorState
}
