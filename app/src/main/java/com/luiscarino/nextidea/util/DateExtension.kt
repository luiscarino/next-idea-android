package com.luiscarino.nextidea.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToMonthDayYear(): String {
    val sdf = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return sdf.format(this)
}