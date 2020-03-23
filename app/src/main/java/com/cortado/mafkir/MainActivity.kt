package com.cortado.mafkir

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import com.cortado.mafkir.model.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mafkirPermissionsValidator.validate(this)

        startService(Intent(this, InteractionsService::class.java))

        setContentView(R.layout.activity_main)
    }
}
