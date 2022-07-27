package com.metinvandar.cryptotrackerapp.presentation.rate_alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.usecase.SaveCoinRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateAlertViewModel @Inject constructor(private val saveCoinRateUseCase: SaveCoinRateUseCase) :
    ViewModel() {

    fun saveCoinRate(coin: CoinDomainModel, minRate: Double, maxRate: Double) {
        viewModelScope.launch {
            saveCoinRateUseCase(coin, minRate, maxRate)
        }
    }
}
