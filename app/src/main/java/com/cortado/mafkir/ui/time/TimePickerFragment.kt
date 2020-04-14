package com.cortado.mafkir.ui.time

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment

class TimePickerFragment(private val listener: TimePickerDialog.OnTimeSetListener) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, listener, 1, 0, DateFormat.is24HourFormat(activity))
    }
}