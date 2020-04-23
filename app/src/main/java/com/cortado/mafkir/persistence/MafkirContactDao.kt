package com.cortado.mafkir.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cortado.mafkir.model.time.Interval

@Dao
@TypeConverters(Converters::class)
interface MafkirContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mafkirContact: MafkirContact)

    @Query("update mafkir_contacts set interval=:interval where contact=:contact")
    fun updateInterval(contact: String, interval: Interval)

    @Query("update mafkir_contacts set lastInteractionMillis=:lastInteraction where contact=:contact")
    fun updateLastInteraction(contact: String, lastInteraction: Long)

    @Query("delete from mafkir_contacts where contact=:contact")
    fun delete(contact: String)

    @Query("select * from mafkir_contacts")
    fun getAll(): LiveData<List<MafkirContact>>
}