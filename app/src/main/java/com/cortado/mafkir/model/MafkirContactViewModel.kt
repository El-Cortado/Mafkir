package com.cortado.mafkir.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.repository.MafkirContactRepository
import javax.inject.Inject

class MafkirContactViewModel @Inject constructor(
    val mafkirContactRepository: MafkirContactRepository
) : ViewModel() {
    private val mafkirContacts: LiveData<List<MafkirContact>> = mafkirContactRepository.getAll()

    fun insert(contact: String, interactionInterval: Long) {
        mafkirContactRepository.insert(contact, interactionInterval)
    }

    fun updateInteractionInterval(contact: String, interactionInterval: Long) {
        mafkirContactRepository.updateInteractionInterval(contact, interactionInterval)
    }

    fun delete(contact: String) {
        mafkirContactRepository.delete(contact)
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return mafkirContacts
    }

}