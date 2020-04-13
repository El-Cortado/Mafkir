package com.cortado.mafkir.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cortado.mafkir.R
import com.cortado.mafkir.databinding.FragmentEditBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.ui.actionbar.ActionBarController
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

    @Inject
    lateinit var actionBarController: ActionBarController

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

        setHasOptionsMenu(true)

        binding.mafkirContact = args.mafkirContact
        binding.timeInterval =
            timeConverter.millisToDays(args.mafkirContact.interactionIntervalMillis).toString()

        setupViewModel()

        binding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_options, menu)
        actionBarController.showBack(this)
        actionBarController.setHeadline(this, "Edit Reminder")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_accept -> {
                mafkirContactViewModel.updateInteractionInterval(
                    binding.editContact.text.toString(),
                    timeConverter.daysToMillis(binding.editInterval.text.toString().toLong())
                )
                Navigation.findNavController(requireView()).popBackStack()
                activity?.currentFocus?.clearFocus()
                true
            }
            R.id.action_delete -> {
                mafkirContactViewModel.delete(
                    binding.editContact.text.toString()
                )
                Navigation.findNavController(requireView()).popBackStack()
                activity?.currentFocus?.clearFocus()
                true
            }
            else -> super.onOptionsItemSelected(item)
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