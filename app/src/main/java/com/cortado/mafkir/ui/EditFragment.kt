package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cortado.mafkir.R
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment: DaggerFragment() {
    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var mafkirContact:MafkirContact

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

        editInterval.apply {
            this.minValue = 1
            this.maxValue = 10
            this.wrapSelectorWheel = true
            this.value = mafkirContact.interactionInterval.toInt()
        }

        setupViewModel()

        editButton.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
        }
    }

    private fun setupViewModel() {
        mafkirContactViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(MafkirContactViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mafkirContactViewModel.updateInteractionInterval(editContact.text.toString(), editInterval.value.toLong())
        (activity as MainActivity).showFloatingButton()
    }
}