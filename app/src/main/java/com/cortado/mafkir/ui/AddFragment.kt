package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cortado.mafkir.R
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import javax.inject.Inject

class AddFragment : DaggerFragment() {
    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = AddFragmentArgs.fromBundle(it)
            addContact.text = safeArgs.chosenContact
        }

        addInterval.apply {
            this.minValue = 1
            this.maxValue = 10
            this.wrapSelectorWheel = true
        }

        setupViewModel()

        addButton.setOnClickListener {
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
        mafkirContactViewModel.insert(addContact.text.toString(), addInterval.value.toLong())
        (activity as MainActivity).showFloatingButton()
    }
}