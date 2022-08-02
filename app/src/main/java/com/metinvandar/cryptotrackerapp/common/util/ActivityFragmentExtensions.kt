package com.metinvandar.cryptotrackerapp.common.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
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
    requireView().snackBar(message = errorMessage, action = retry, duration = errorViewDuration)
}

fun Activity.hideKeyboard() {
    val view = currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view != null) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}