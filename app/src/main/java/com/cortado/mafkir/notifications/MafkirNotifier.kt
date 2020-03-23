package com.cortado.mafkir.notifications

import android.app.Application
import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cortado.mafkir.R

class MafkirNotifier(application: Application, channelId: String) {

    private val mApplication = application;
    private val mChannelId = channelId;

    fun build(title: String, body: String, onGoing: Boolean): Notification {
        return NotificationCompat.Builder(mApplication, mChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setOngoing(onGoing)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
    }

    fun notify(title: String, body: String, id: Int, onGoing: Boolean) {
        with(NotificationManagerCompat.from(mApplication)) {
            notify(id, build(title, body, onGoing))
        }
    }
}