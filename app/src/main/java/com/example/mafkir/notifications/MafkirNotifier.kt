package com.example.mafkir.notifications

import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mafkir.R

class MafkirNotifier(application: Application, channelId: String) {

    private val mApplication = application;
    private val mChannelId = channelId;

    fun notify(title: String, body: String, id: Int) {
        val builder = NotificationCompat.Builder(mApplication, mChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(mApplication)) {
            notify(1337, builder.build())
        }
    }
}