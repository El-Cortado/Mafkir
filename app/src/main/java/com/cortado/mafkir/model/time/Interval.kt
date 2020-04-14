package com.cortado.mafkir.model.time

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Interval(
    var type: IntervalType,
    var interval: Int,
    var days: Array<Boolean>,
    var timeOfDay: Pair<Int, Int>
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Interval

        if (type != other.type) return false
        if (interval != other.interval) return false
        if (!days.contentEquals(other.days)) return false
        if (timeOfDay != other.timeOfDay) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + interval
        result = 31 * result + days.contentHashCode()
        result = 31 * result + timeOfDay.hashCode()
        return result
    }
}