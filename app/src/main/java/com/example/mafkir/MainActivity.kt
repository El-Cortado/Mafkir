package com.example.mafkir

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mafkir.notifications.MafkirNotifier
import com.example.mafkir.notifications.NotificationRepository
import com.example.mafkir.notifications.NotificationsService
import com.example.mafkir.permissions.MafkirPermissionsValidator
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mMafkirNotifier: MafkirNotifier

    @Inject
    lateinit var mMafkirPermissionsValidator: MafkirPermissionsValidator

    @Inject
    lateinit var mNotificationRepository: NotificationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mMafkirPermissionsValidator.validate(this)
        startService(Intent(this, NotificationsService::class.java))
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.testNotification)
        button.setOnClickListener {
            mMafkirNotifier.notify("text", "body", Math.random().toInt());
        }
    }
}
