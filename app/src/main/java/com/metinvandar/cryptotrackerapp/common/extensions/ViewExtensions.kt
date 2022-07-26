package com.metinvandar.cryptotrackerapp.common.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: String, action: (() -> Unit)? = null, duration: Int = Snackbar.LENGTH_INDEFINITE) {
    val snackBar = Snackbar.make(this, message, duration)
    action?.let {
        snackBar.setAction("Retry") {
            it()
            snackBar.dismiss()
        }
    }
    snackBar.show()
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(visible) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }