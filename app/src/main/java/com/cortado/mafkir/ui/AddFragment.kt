package com.cortado.mafkir.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cortado.mafkir.R
import com.cortado.mafkir.databinding.FragmentAddBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.TimeConverter
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.ui.actionbar.ActionBarController
import com.cortado.mafkir.ui.picker.TimePickerFragment
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
    lateinit var actionBarController: ActionBarController

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

        weekdaysButtons.children.forEach { v -> weekdaysButtons.check(v.id) }

        setHasOptionsMenu(true)

        val adapter = ArrayAdapter<String>(context!!, R.layout.spinner_item, arrayOf("week", "day"))
        intervalTypeDropdown.setAdapter(adapter)

        binding.chosenContact = args.chosenContact
        binding.chosenTime = "1:00"
        binding.timePickerOnClick = View.OnClickListener {
            activity?.supportFragmentManager?.let {
                TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    binding.chosenTime = "$hourOfDay:${if (minute < 10) "0$minute" else "$minute"}"
                    binding.executePendingBindings()
                }
                ).show(it, "Time Picker")
            }
        }

        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_options, menu)
        actionBarController.showBack(this)
        actionBarController.setHeadline(this, "New Reminder")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_accept -> {
                mafkirContactViewModel.insert(
                    binding.addContact.text.toString(),
                    timeConverter.daysToMillis(binding.addInterval.text.toString().toLong())
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