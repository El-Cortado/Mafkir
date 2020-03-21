package com.example.mafkir.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MafkirContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mafkirContact: MafkirContact)

    @Update
    fun update(mafkirContact: MafkirContact)

    @Delete
    fun delete(mafkirContact: MafkirContact)

    @Query("select * from mafkir_contacts where contact = :contact")
    fun get(contact: String): MafkirContact

    @Query("select * from mafkir_contacts")
    fun getAll(): LiveData<List<MafkirContact>>
}