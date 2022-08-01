package com.metinvandar.cryptotrackerapp.domain

import com.metinvandar.cryptotrackerapp.domain.model.ErrorType

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val error: ErrorType) : Resource<Nothing>()
    class Loading(val status: Boolean) : Resource<Nothing>()
}
