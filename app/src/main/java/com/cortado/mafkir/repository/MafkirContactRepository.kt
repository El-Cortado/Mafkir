package com.cortado.mafkir.repository

import androidx.lifecycle.LiveData
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.persistence.MafkirContactDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MafkirContactRepository @Inject constructor(private val mafkirContactDao: MafkirContactDao) {
    private val allContacts = mafkirContactDao.getAll()

    fun insert(contact: String, interactionIntervalDays: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.insert(
                MafkirContact(
                    0,
                    contact,
                    interactionIntervalDays,
                    System.currentTimeMillis()
                )
            )
        }
    }

    fun updateLastInteraction(contact: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.updateLastInteraction(contact, System.currentTimeMillis())
        }
    }

    fun updateInteractionInterval(contact: String, interactionIntervalDays: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.updateInteractionInterval(contact, interactionIntervalDays)
        }
    }

    fun delete(contact: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.delete(contact)
        }
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return allContacts
    }
}