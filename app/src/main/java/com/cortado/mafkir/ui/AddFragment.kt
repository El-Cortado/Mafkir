package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cortado.mafkir.databinding.FragmentAddBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.google.android.material.transition.MaterialContainerTransform
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add.*
import javax.inject.Inject

class AddFragment : DaggerFragment() {
    private lateinit var binding: FragmentAddBinding

    private val args: AddFragmentArgs by navArgs()

    private lateinit var mafkirContactViewModel: MafkirContactViewModel

    @Inject
    lateinit var timeConverter: TimeConverter

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chosenContact = args.chosenContact

        setupViewModel()

        addButton.setOnClickListener {
            mafkirContactViewModel.insert(
                binding.addContact.text.toString(),
                timeConverter.daysToMillis(binding.addInterval.text.toString().toLong())
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