package com.example.mafkir

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mafkir.notifications.NotificationRegistry
import com.example.mafkir.notifications.NotificationsService
import com.google.common.eventbus.EventBus
import dagger.android.AndroidInjection
import javax.inject.Inject
import javax.inject.Named


class MainActivity : AppCompatActivity() {

    @Inject
    @field:Named("Notifications")
    lateinit var mEventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        checkForPermissions()
        mEventBus.register(NotificationRegistry())
        createNotificationChannel()
        startService(Intent(this, NotificationsService::class.java))
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.testNotification)
        button.setOnClickListener {
            onClickNotify()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                this.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun onClickNotify() {
        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1337, builder.build())
        }
    }

    private fun checkForPermissions() {
        val notificationListenerString: String =
            Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners")
        if (!notificationListenerString.contains(packageName)) {
            alertPermissions()
        }
    }

    private fun alertPermissions() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Permissions")
            .setMessage("In order for our app to work we need you to enable notification listening")
            .setPositiveButton("Ok") { _, _ ->
                // Do something when user press the positive button
                Toast.makeText(
                    applicationContext,
                    "Please enable notification for Mafkir.",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
            .setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(
                    applicationContext,
                    "Our app cant rum without these permissions.",
                    Toast.LENGTH_SHORT
                ).show()
                this.finishAffinity()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
