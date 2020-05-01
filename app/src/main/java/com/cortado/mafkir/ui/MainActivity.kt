package com.cortado.mafkir.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.cortado.mafkir.R
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.notifications.MafkirNotifier
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import com.cortado.mafkir.ui.actionbar.ActionBarController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator
    @Inject
    lateinit var actionBarController: ActionBarController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mafkirPermissionsValidator.validate(this)

        startService(Intent(this, InteractionsService::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                actionBarController.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
