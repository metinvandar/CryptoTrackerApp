package com.metinvandar.cryptotrackerapp.presentation.set_alert

sealed class InputState {
    data class MinRateError(val errorMessage: String): InputState()
    data class MaxRateError(val errorMessage: String): InputState()
    data class AllInputsError(val errorMessage: String): InputState()
    object Valid: InputState()
    object Initial: InputState()
}
