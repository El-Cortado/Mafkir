package com.cortado.mafkir.persistence

import androidx.room.TypeConverter
import com.cortado.mafkir.model.time.Interval
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun toInterval(value: String?): Interval? {
        return Gson().fromJson(value, Interval::class.java)
    }

    @TypeConverter
    fun fromInterval(interval: Interval?): String? {
        return Gson().toJson(interval)
    }
}