package com.cortado.mafkir.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cortado.mafkir.R
import com.cortado.mafkir.databinding.FragmentEditBinding
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.model.ViewModelProviderFactory
import com.cortado.mafkir.model.time.Interval
import com.cortado.mafkir.model.time.IntervalType
import com.cortado.mafkir.ui.actionbar.ActionBarController
import com.cortado.mafkir.ui.adapter.NoFilterAdapter
import com.cortado.mafkir.ui.time.TimePickerFragment
import com.google.android.material.transition.MaterialContainerTransform
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject


class EditFragment : DaggerFragment() {

    private val args: EditFragmentArgs by navArgs()

    private lateinit var binding: FragmentEditBinding

    private lateinit var mafkirContactViewModel: MafkirContactViewModel

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

        setupBinding()

        setupWeekdaysToggleGroup()

        setupInterval()

        setupIntervalType()

        setupViewModel()

    }

    private fun setupBinding() {
        if (args.editExisting) {
            args.mafkirContact!!.let {
                binding.cardTransitionName = getString(R.string.edit_transition_name).format(it.id)
                binding.contactName = args.chosenContact
                binding.timeInterval = it.interval
            }
        } else {
            binding.cardTransitionName = getString(R.string.add_transition_name)
            binding.contactName = args.chosenContact
            binding.timeInterval = Interval(
                IntervalType.WEEK,
                1,
                arrayOf(true, true, true, true, true, true, false),
                Pair(1, 0)
            )
        }

        updateTimeOfDay()

        binding.timePickerOnClick = View.OnClickListener {
            TimePickerFragment(
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    binding.timeInterval?.timeOfDay = Pair(hourOfDay, minute)
                    updateTimeOfDay()
                    binding.executePendingBindings()
                },
                binding.timeInterval!!.timeOfDay
            ).show(parentFragmentManager, "TimePicker")
        }
        binding.executePendingBindings()
    }

    private fun updateTimeOfDay() {
        binding.timeOfDay = getString(R.string.timeOfDayFormat).format(
            binding.timeInterval?.timeOfDay?.first,
            binding.timeInterval?.timeOfDay?.second
        )
    }

    private fun setupWeekdaysToggleGroup() {
        blacklistWeekdaysButtons.children.forEachIndexed { i, v ->
            if (!binding.timeInterval!!.days[i]) {
                blacklistWeekdaysButtons.check(v.id)
            }
        }

        blacklistWeekdaysButtons.addOnButtonCheckedListener { _, checkedId, isChecked ->
            binding.timeInterval!!.days[
                    blacklistWeekdaysButtons.indexOfChild(activity?.findViewById(checkedId))
            ] = !isChecked
        }
    }

    private fun setupInterval() {
        intervalTypeDropdown.setText(binding.timeInterval?.interval.toString())
        addInterval.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = addInterval.text?.toString()?.trim()!!

                if (text.isEmpty() || text.toInt() == 0) {
                    addInterval.setText("1")
                    binding.timeInterval?.interval = 1
                } else {
                    binding.timeInterval?.interval = text.toInt()
                }
            }
        }
    }

    private fun setupIntervalType() {
        val intervalTypes = IntervalType.values().map { v -> v.text }
        val adapter =
            NoFilterAdapter(context!!, R.layout.spinner_item, intervalTypes.toTypedArray())
        intervalTypeDropdown.setAdapter(adapter)
        intervalTypeDropdown.setText(binding.timeInterval?.type?.text)

        intervalTypeDropdown.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                binding.timeInterval?.type = IntervalType.fromText(text.toString())
                binding.executePendingBindings()
            }
        })
    }

    private fun setupViewModel() {
        mafkirContactViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(MafkirContactViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        actionBarController.showBack(this)

        if (args.editExisting) {
            inflater.inflate(R.menu.edit_options, menu)
            actionBarController.setHeadline(this, "Edit Reminder")
        } else {
            inflater.inflate(R.menu.add_options, menu)
            actionBarController.setHeadline(this, "New Reminder")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_accept_new -> {
                if (verifyForm()) {
                    mafkirContactViewModel.insert(
                        args.chosenContact,
                        binding.timeInterval!!
                    )
                    activity?.currentFocus?.clearFocus()
                    Navigation.findNavController(requireView()).popBackStack()
                    informNewReminder()
                }
                true
            }
            R.id.action_accept_edit -> {
                if (verifyForm()) {
                    mafkirContactViewModel.updateInteractionInterval(
                        args.chosenContact,
                        binding.timeInterval!!
                    )
                    activity?.currentFocus?.clearFocus()
                    Navigation.findNavController(requireView()).popBackStack()
                    informNewReminder()
                }
                true
            }
            R.id.action_delete -> {
                mafkirContactViewModel.delete(
                    args.chosenContact
                )
                activity?.currentFocus?.clearFocus()
                Navigation.findNavController(requireView()).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun verifyForm(): Boolean {
        if (!binding.timeInterval!!.days.contains(true)
        ) {
            Toast.makeText(
                context!!,
                "Please allow at least one day",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun informNewReminder() {
        Toast.makeText(
            context!!,
            "We will remind you to contact ${binding.contactName} every " +
                    "${binding.timeInterval?.interval} ${binding.timeInterval?.type?.text}" +
                    " at ${getString(
                        R.string.timeOfDayFormat
                    ).format(
                        binding.timeInterval?.timeOfDay?.first,
                        binding.timeInterval?.timeOfDay?.second
                    )}" +
                    if (binding.timeInterval?.days!!.contains(false)) " but not on ${getBlacklistDaysText()}" else "",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getBlacklistDaysText(): String {
        val days =
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        return binding.timeInterval?.days
            ?.mapIndexed { i, isDayActive -> if (!isDayActive) days[i] else "" }
            ?.filter { s -> s.isNotEmpty() }
            ?.reduce { s1, s2 -> "$s1, $s2" }!!
    }
}