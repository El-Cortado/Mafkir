package com.cortado.mafkir.ui

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import com.cortado.mafkir.R
import com.cortado.mafkir.model.time.IntervalType
import com.cortado.mafkir.notifications.InteractionsService
import com.cortado.mafkir.notifications.MafkirNotifier
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import com.cortado.mafkir.persistence.MafkirContact
import com.cortado.mafkir.ui.actionbar.ActionBarController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mafkirPermissionsValidator: MafkirPermissionsValidator
    @Inject
    lateinit var actionBarController: ActionBarController
    @field:[Inject Named("User")]
    lateinit var mafkirNotifier: MafkirNotifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mafkirPermissionsValidator.validate(this)

        startService(Intent(this, InteractionsService::class.java))
    }

    fun sendNotification(contact: MafkirContact) {
        val contactName = contact.contact
        val contactPhoneNumber = contact.phoneNumber.trim()
        val daysSinceLastContact =
            (System.currentTimeMillis() - contact.lastInteractionMillis) / IntervalType.DAY.millisInUnit

        val uri = "tel:$contactPhoneNumber"
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse(uri)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, dialIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        mafkirNotifier.notify(
            100, mafkirNotifier.build(
                "Keep in contact with $contactName!",
                "You haven't talked to $contactName in $daysSinceLastContact days",
                false,
                listOf(
                    NotificationCompat.Action(
                        R.drawable.contact_icon,
                        "CALL $contactName",
                        pendingIntent
                    ),
                    NotificationCompat.Action(
                        R.drawable.contact_icon,
                        "MESSAGE $contactName",
                        pendingIntent
                    )
                )
            )
        )
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
