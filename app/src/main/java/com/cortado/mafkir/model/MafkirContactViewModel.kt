package com.cortado.mafkir.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.repository.MafkirContactRepository
import javax.inject.Inject

class MafkirContactViewModel @Inject constructor(
    private val mafkirContactRepository: MafkirContactRepository
) : ViewModel() {
    private val mafkirContacts: LiveData<List<MafkirContact>> = mafkirContactRepository.getAll()

    fun insert(mafkirContact: MafkirContact) {
        mafkirContactRepository.insert(mafkirContact)
    }

    fun update(mafkirContact: MafkirContact) {
        mafkirContactRepository.update(mafkirContact)
    }

    fun delete(mafkirContact: MafkirContact) {
        mafkirContactRepository.delete(mafkirContact)
    }

    fun getAll(): LiveData<List<MafkirContact>> {
        return mafkirContacts
    }

}