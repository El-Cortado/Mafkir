package com.example.mafkir.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.mafkir.Constants
import com.example.mafkir.notifications.MafkirNotifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MafkirNotifierModule {
    @Singleton
    @Provides
    fun providesNotificationMafkirNotifier(app: Application): MafkirNotifier {
        createNotificationChannel(app)
        return MafkirNotifier(app, Constants.CHANNEL_ID)
    }

    private fun createNotificationChannel(app: Application) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Mafkir channel"
            val descriptionText = "Mafkir app notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}