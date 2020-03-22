package com.cortado.mafkir

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cortado.mafkir.model.MafkirContactViewModel
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.notifications.MafkirNotifier
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import com.cortado.mafkir.util.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named


class MainActivity : DaggerAppCompatActivity() {
    @field:[Inject Named("User")]
    lateinit var mafkirNotifier: MafkirNotifier

    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var mafkirContactViewModel: MafkirContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mafkirPermissionsValidator.validate(this)

        startService(Intent(this, InteractionsService::class.java))

        setContentView(R.layout.activity_main)

        mafkirContactViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(MafkirContactViewModel::class.java)

        mafkirContactViewModel.getAll().observe(this, Observer { lisOfNotes ->
            lisOfNotes?.let {
                lisOfNotes.iterator().forEach {
                    Log.i("Mafkir", "contact:$it")
                }
            }
        })

        val button = findViewById<Button>(R.id.testNotification)
        button.setOnClickListener {
            //
        }

    }
}
