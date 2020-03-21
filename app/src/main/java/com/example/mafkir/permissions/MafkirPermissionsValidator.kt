package com.example.mafkir.permissions

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import javax.inject.Inject

class MafkirPermissionsValidator @Inject constructor(private val application: Application) {

    fun validate(activity: Activity) {
        val notificationListenerString: String =
            Settings.Secure.getString(application.contentResolver, "enabled_notification_listeners")
        if (!notificationListenerString.contains(application.packageName)) {
            alertPermissions(activity)
        }
    }

    private fun alertPermissions(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
            .setTitle("Permissions")
            .setMessage("In order for our app to work we need you to enable notification listening")
            .setPositiveButton("Ok") { _, _ ->
                Toast.makeText(
                    application,
                    "Please enable notification for Mafkir.",
                    Toast.LENGTH_LONG
                ).show()
                application.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            .setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(
                    application,
                    "Our app cant run without these permissions.",
                    Toast.LENGTH_LONG
                ).show()
                activity.finishAffinity()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}