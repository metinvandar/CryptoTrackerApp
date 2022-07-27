package com.metinvandar.cryptotrackerapp.common.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(
    message: String,
    actionButtonText: String? = null,
    action: (() -> Unit)? = null,
    duration: Int = Snackbar.LENGTH_INDEFINITE
) {
    val snackBar = Snackbar.make(this, message, duration)
    snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackBar.setAction(actionButtonText ?: "Retry") {
        action?.invoke()
        snackBar.dismiss()
    }
    snackBar.show()
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(visible) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }
