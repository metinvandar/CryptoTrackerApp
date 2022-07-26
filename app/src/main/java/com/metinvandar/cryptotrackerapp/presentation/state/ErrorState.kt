package com.metinvandar.cryptotrackerapp.presentation.state

import com.metinvandar.cryptotrackerapp.domain.model.ErrorType

interface ErrorState {
    val errorType: ErrorType
}
