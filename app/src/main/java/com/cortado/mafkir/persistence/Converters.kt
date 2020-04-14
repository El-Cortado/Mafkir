package com.cortado.mafkir.persistence

import androidx.room.TypeConverter
import com.cortado.mafkir.model.time.Interval
import com.cortado.mafkir.model.time.IntervalType

class Converters {
    @TypeConverter
    fun fromInterval(value: ByteArray?): Interval? {
        return Interval(IntervalType.WEEK, 0, arrayOf(true), Pair(1,1))
    }

    @TypeConverter
    fun dateToTimestamp(date: Interval?): ByteArray? {
        return ByteArray(2)
    }
}