package com.metinvandar.cryptotrackerapp.common.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.getFormattedDate(): String {
    val dateFormat = "d/MM/yyyy HH:mm:ss"
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}
