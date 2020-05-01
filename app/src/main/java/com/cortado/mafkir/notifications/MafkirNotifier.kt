package com.cortado.mafkir.notifications

import android.app.Application
import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cortado.mafkir.R

class MafkirNotifier(application: Application, channelId: String) {

    private val mApplication = application
    private val mChannelId = channelId

    fun build(
        title: String,
        body: String,
        onGoing: Boolean,
        actions: List<NotificationCompat.Action> = ArrayList()
    ): Notification {
        val builder = NotificationCompat.Builder(mApplication, mChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setOngoing(onGoing)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        actions.forEach { builder.addAction(it) }

        return builder.build()
    }

    fun notify(title: String, body: String, id: Int, onGoing: Boolean) {
        notify(id, build(title, body, onGoing))
    }

    fun notify(id: Int, notification: Notification) {
        with(NotificationManagerCompat.from(mApplication)) {
            notify(id, notification)
        }
    }
}