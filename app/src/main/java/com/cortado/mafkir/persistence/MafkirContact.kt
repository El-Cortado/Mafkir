package com.cortado.mafkir.persistence

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mafkir_contacts")
data class MafkirContact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var contact: String,
    var interactionInterval: Long,
    var lastInteraction: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(contact)
        parcel.writeLong(interactionInterval)
        parcel.writeLong(lastInteraction)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MafkirContact> {
        override fun createFromParcel(parcel: Parcel): MafkirContact {
            return MafkirContact(parcel)
        }

        override fun newArray(size: Int): Array<MafkirContact?> {
            return arrayOfNulls(size)
        }
    }
}