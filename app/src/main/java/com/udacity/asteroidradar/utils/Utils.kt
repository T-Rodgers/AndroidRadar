package com.udacity.asteroidradar

import com.udacity.asteroidradar.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDay(): String {
    val calendar = Calendar.getInstance()
    return formatDate(calendar.time)
}

fun getWeekFromCurrentDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}

private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}