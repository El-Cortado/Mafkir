package com.example.mafkir.notifications

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.common.eventbus.EventBus
import dagger.android.AndroidInjection
import javax.inject.Inject

class NotificationsService : NotificationListenerService() {
    @Inject
    lateinit var mEventBus:EventBus;

    override fun onCreate() {
        AndroidInjection.inject(this);
        super.onCreate()
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        mEventBus.post(sbn);
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        mEventBus.post(sbn);
    }
}
