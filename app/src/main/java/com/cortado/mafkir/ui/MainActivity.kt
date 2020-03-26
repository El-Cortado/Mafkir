package com.cortado.mafkir.ui

import android.content.Intent
import android.os.Bundle
import com.cortado.mafkir.R
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mafkirPermissionsValidator.validate(this)

        setContentView(R.layout.activity_main)

        startService(Intent(this, InteractionsService::class.java))
    }
}
