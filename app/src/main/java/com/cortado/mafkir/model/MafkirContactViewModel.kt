package com.cortado.mafkir.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.repository.MafkirContactRepository
import javax.inject.Inject

class MafkirContactViewModel @Inject constructor(
    private val mafkirContactRepository: MafkirContactRepository
) : ViewModel() {

    @Inject
    lateinit var timeConverter: TimeConverter

    private val mafkirContacts: LiveData<List<MafkirContact>> = mafkirContactRepository.getAll()

    fun insert(contact: String, interactionIntervalDays: Long) {
        mafkirContactRepository.insert(contact, timeConverter.daysToMillis(interactionIntervalDays))
    }

    fun updateInteractionInterval(contact: String, interactionIntervalDays: Long) {
        mafkirContactRepository.updateInteractionInterval(contact, timeConverter.daysToMillis(interactionIntervalDays))
    }

    fun delete(contact: String) {
        mafkirContactRepository.delete(contact)
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return mafkirContacts
    }

}