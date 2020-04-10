package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cortado.mafkir.databinding.FragmentEditBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.google.android.material.transition.MaterialContainerTransform
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditFragment : DaggerFragment() {

    private val args: EditFragmentArgs by navArgs()

    private lateinit var binding: FragmentEditBinding

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
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mafkirContact = args.mafkirContact
        binding.timeInterval =
            timeConverter.millisToDays(args.mafkirContact.interactionIntervalMillis).toString()

        setupViewModel()

        binding.clickListener = View.OnClickListener {
            mafkirContactViewModel.updateInteractionInterval(
                binding.editContact.text.toString(),
                timeConverter.daysToMillis(binding.editInterval.text.toString().toLong())
            )
            Navigation.findNavController(requireView()).popBackStack()
        }

        binding.executePendingBindings()
    }

    private fun setupViewModel() {
        mafkirContactViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(MafkirContactViewModel::class.java)
    }
}