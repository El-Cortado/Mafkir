package com.cortado.mafkir.util

import java.text.DateFormat.getDateTimeInstance
import java.util.*

class DateConverter {
    fun getDate(milliSeconds: Long): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = getDateTimeInstance()

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}