package com.cortado.mafkir.ui.actionbar

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import javax.inject.Inject

class ActionBarController @Inject constructor() {
    var backCallback: () -> Unit = {}

    fun showBack(fragment: Fragment) {
        setBackEnabled(fragment, true)
        backCallback = { Navigation.findNavController(fragment.requireView()).popBackStack() }
    }

    fun hideBack(fragment: Fragment) {
        setBackEnabled(fragment, false)
    }

    private fun setBackEnabled(fragment: Fragment, value: Boolean) {
        (fragment.activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(value)
        (fragment.activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(value)
    }

    fun onBackPressed() {
        backCallback()
    }

    fun setHeadline(fragment: Fragment, headline: String) {
        (fragment.activity as AppCompatActivity).supportActionBar?.title = headline
    }
}