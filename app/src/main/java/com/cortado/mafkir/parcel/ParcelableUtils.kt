package com.cortado.mafkir.parcel

import android.os.Parcel

import android.os.Parcelable


fun marshall(parceable: Parcelable): ByteArray {
    val parcel = Parcel.obtain()
    parceable.writeToParcel(parcel, 0)
    val bytes = parcel.marshall()
    parcel.recycle()
    return bytes
}

fun <T : Parcelable?> unmarshall(bytes: ByteArray, creator: Parcelable.Creator<T>): T {
    val parcel = unmarshall(bytes)
    val result = creator.createFromParcel(parcel)
    parcel.recycle()
    return result
}

fun unmarshall(bytes: ByteArray): Parcel {
    val parcel = Parcel.obtain()
    parcel.unmarshall(bytes, 0, bytes.size)
    parcel.setDataPosition(0)
    return parcel
}
