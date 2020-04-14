package com.cortado.mafkir.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cortado.mafkir.model.time.Interval
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.repository.MafkirContactRepository
import javax.inject.Inject

class MafkirContactViewModel @Inject constructor(
    private val mafkirContactRepository: MafkirContactRepository
) : ViewModel() {

    fun insert(contact: String, interval: Interval) {
        mafkirContactRepository.insert(contact, interval)
    }

    fun updateInteractionInterval(contact: String, interval: Interval) {
        mafkirContactRepository.updateInteractionInterval(
            contact,
            interval
        )
    }

    fun delete(contact: String) {
        mafkirContactRepository.delete(contact)
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return mafkirContactRepository.getAll()
    }

}