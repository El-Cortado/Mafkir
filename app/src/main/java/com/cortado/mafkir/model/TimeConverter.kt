package com.cortado.mafkir.model

import java.text.DateFormat.getDateTimeInstance
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeConverter @Inject constructor() {
    fun millisToDate(milliSeconds: Long): String? {
        val formatter = getDateTimeInstance()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun millisToDays(milliSeconds: Long): Long {
        return TimeUnit.DAYS.convert(milliSeconds, TimeUnit.MILLISECONDS)
    }

    fun daysToMillis(days: Long): Long {
        return TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS)
    }
}