package com.cortado.mafkir.notifications

import android.app.Notification
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.common.eventbus.Subscribe

class NotificationRegistry(notificationRepository: NotificationRepository) {
    //TODO: do something with the notification
    val mNotificationRepository = notificationRepository

    @Subscribe
    fun statusBarNotificationEvent(statusBarNotification: StatusBarNotification) {
        val text =
            statusBarNotification.notification.extras.getCharSequence(Notification.EXTRA_TEXT)
                .toString()
        val title =
            statusBarNotification.notification.extras.getCharSequence(Notification.EXTRA_TITLE)
                .toString()
        Log.i("Mafkir", text)
        Log.i("Mafkir", title)
        Log.i("Mafkir", statusBarNotification.packageName)
    }
}