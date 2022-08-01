package com.metinvandar.cryptotrackerapp.presentation.set_alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.common.ResourceManager
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.usecase.SaveCoinAlertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetAlertViewModel @Inject constructor(
    private val saveCoinAlertUseCase: SaveCoinAlertUseCase,
    private val resourceManager: ResourceManager
) :
    ViewModel() {

    private val _inputState = MutableStateFlow<InputState>(InputState.Initial)
    val inputState get() = _inputState.asStateFlow()

    fun saveCoinAlert(coin: CoinDomainModel, minRate: Double, maxRate: Double) {
        viewModelScope.launch {
            saveCoinAlertUseCase(coin, minRate, maxRate)
        }
    }

    fun validateInputs(minRate: String?, maxRate: String?) {
        if (minRate.isNullOrBlank()) {
            _inputState.value =
                InputState.MinRateError(errorMessage = resourceManager.getString(R.string.min_rate_input_empty_error))
        } else if (maxRate.isNullOrBlank()) {
            _inputState.value =
                InputState.MaxRateError(errorMessage = resourceManager.getString(R.string.max_rate_input_empty_error))
        } else if (maxRate.toString().toDouble() <= minRate.toString().toDouble()) {
            _inputState.value =
                InputState.AllInputsError(errorMessage = resourceManager.getString(R.string.max_rate_must_be_greater))
        } else {
            _inputState.value = InputState.Valid
        }

    }
}
