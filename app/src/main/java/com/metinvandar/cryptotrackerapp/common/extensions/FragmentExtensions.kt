package com.metinvandar.cryptotrackerapp.common.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.metinvandar.cryptotrackerapp.R
import com.metinvandar.cryptotrackerapp.domain.model.ErrorType
import com.metinvandar.cryptotrackerapp.presentation.state.ErrorState

fun Fragment.handleError(
    errorState: ErrorState,
    retry: (() -> Unit)? = null,
    errorViewDuration: Int = Snackbar.LENGTH_INDEFINITE
) {
    val errorMessage = when (errorState.errorType) {
        ErrorType.NETWORK_ERROR -> {
            getString(R.string.connection_error_message)
        }
        ErrorType.UNKNOWN_ERROR -> {
            getString(R.string.unknown_error_message)
        }
    }
    requireView().snackBar(errorMessage, retry, errorViewDuration)
}
