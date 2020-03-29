package com.cortado.mafkir.persistence

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "mafkir_contacts")
data class MafkirContact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var contact: String,
    var interactionInterval: Long,
    var lastInteraction: Long
) : Parcelable