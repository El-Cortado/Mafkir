package com.cortado.mafkir.persistence

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cortado.mafkir.model.time.Interval
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "mafkir_contacts")
@TypeConverters(Converters::class)
data class MafkirContact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var contact: String,
    var interval: Interval,
    var lastInteractionMillis: Long
) : Parcelable