package com.cortado.mafkir.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MafkirContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mafkirContact: MafkirContact)

    @Query("update mafkir_contacts set interactionIntervalMillis=:interactionInterval where contact=:contact")
    fun updateInteractionInterval(contact: String, interactionInterval: Long)

    @Query("update mafkir_contacts set lastInteractionMillis=:lastInteraction where contact=:contact")
    fun updateLastInteraction(contact: String, lastInteraction: Long)

    @Query("delete from mafkir_contacts where contact=:contact")
    fun delete(contact: String)

    @Query("select * from mafkir_contacts")
    fun getAll(): LiveData<List<MafkirContact>>
}