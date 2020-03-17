package com.example.mafkir.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.google.common.eventbus.EventBus
import dagger.android.AndroidInjection
import javax.inject.Inject
import javax.inject.Named

class NotificationsService : NotificationListenerService() {
    @Inject @field:Named("Notifications")
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
