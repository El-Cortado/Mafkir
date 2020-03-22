package com.cortado.mafkir.notifications

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.cortado.mafkir.repository.MafkirContactRepository
import dagger.android.AndroidInjection
import javax.inject.Inject
import javax.inject.Named


class InteractionsService : NotificationListenerService() {
    //TODO: add phone call interaction handling
    @field:[Inject Named("Silent")]
    lateinit var mafkirNotifier: MafkirNotifier

    @Inject
    lateinit var mafkirContactRepository: MafkirContactRepository

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val notification = mafkirNotifier.build(
            "Mafkir",
            "mafkir has got your back, were here to help you not forget",
            true
        )
        startForeground(1337, notification)
        return START_STICKY
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        //
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // TODO: extract to subscribers
        if (sbn.packageName == "com.whatsapp") {
            sbn.notification.extras.getCharSequence(
                Notification.EXTRA_TITLE
            )?.toString()?.apply {
                mafkirContactRepository.updateLastInteraction(this)
            }
        }
    }
}
