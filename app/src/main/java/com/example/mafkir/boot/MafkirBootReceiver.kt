package com.example.mafkir.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mafkir.notifications.NotificationsService

class MafkirBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(Intent.ACTION_BOOT_COMPLETED == intent.action) {
            context.startService(Intent(context, NotificationsService::class.java))
        }
    }
}
