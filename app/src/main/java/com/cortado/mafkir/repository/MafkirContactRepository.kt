package com.cortado.mafkir.repository

import androidx.lifecycle.LiveData
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.persistence.MafkirContactDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MafkirContactRepository @Inject constructor(private val mafkirContactDao: MafkirContactDao) {
    fun insert(mafkirContact: MafkirContact) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.insert(mafkirContact)
        }
    }

    fun update(mafkirContact: MafkirContact) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.insert(mafkirContact)
        }
    }

    fun delete(mafkirContact: MafkirContact) {
        CoroutineScope(Dispatchers.IO).launch {
            mafkirContactDao.insert(mafkirContact)
        }
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return mafkirContactDao.getAll()
    }
}