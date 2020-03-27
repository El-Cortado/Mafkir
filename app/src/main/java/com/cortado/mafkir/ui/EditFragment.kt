package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cortado.mafkir.R
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.persistence.MafkirContact
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment : DaggerFragment() {
    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var mafkirContact: MafkirContact

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = EditFragmentArgs.fromBundle(it)
            mafkirContact = safeArgs.mafkirContact
        }

        editContact.text = mafkirContact.contact

        editInterval.let {
            it.minValue = 1
            it.maxValue = 10
            it.wrapSelectorWheel = true
            it.value = mafkirContact.interactionInterval.toInt()
        }

        setupViewModel()

        editButton.setOnClickListener {
            mafkirContactViewModel.updateInteractionInterval(
                editContact.text.toString(),
                editInterval.value.toLong()
            )
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun setupViewModel() {
        mafkirContactViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(MafkirContactViewModel::class.java)
    }
}