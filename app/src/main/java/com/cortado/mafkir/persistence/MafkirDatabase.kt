package com.cortado.mafkir.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MafkirContact::class], version = 1)
abstract class MafkirDatabase : RoomDatabase() {
    abstract fun mafkirContactDao(): MafkirContactDao
}