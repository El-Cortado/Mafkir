package com.cortado.mafkir.di

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.cortado.mafkir.Constants
import com.cortado.mafkir.notifications.MafkirNotifier
import com.cortado.mafkir.permissions.MafkirPermissionsValidator
import com.cortado.mafkir.persistence.MafkirContactDao
import com.cortado.mafkir.persistence.MafkirDatabase
import com.cortado.mafkir.repository.MafkirContactRepository
import com.cortado.mafkir.ui.actionbar.ActionBarController
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesMafkirDatabase(app: Application): MafkirDatabase {
        return Room.databaseBuilder(app, MafkirDatabase::class.java, "MafkirDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesMafkirContactDao(db: MafkirDatabase): MafkirContactDao {
        return db.mafkirContactDao()
    }

    @Singleton
    @Provides
    fun providesMafkirContactRepository(mafkirContactDao: MafkirContactDao): MafkirContactRepository {
        return MafkirContactRepository(mafkirContactDao)
    }

    @Singleton
    @Provides
    @Named("User")
    fun providesNotificationMafkirNotifier(app: Application): MafkirNotifier {
        registerNotificationChannel(app)
        return MafkirNotifier(app, Constants.CHANNEL_ID)
    }

    @Singleton
    @Provides
    @Named("Silent")
    fun providesSilentNotificationMafkirNotifier(app: Application): MafkirNotifier {
        registerSilentNotificationChannel(app)
        return MafkirNotifier(app, Constants.SILENT_CHANNEL_ID)
    }

    private fun registerNotificationChannel(app: Application) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Mafkir channel"
            val descriptionText = "Mafkir app notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).also {
                it.description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun registerSilentNotificationChannel(app: Application) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Mafkir silent channel"
            val descriptionText = "Mafkir persistent service silent channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(Constants.SILENT_CHANNEL_ID, name, importance).also {
                it.description = descriptionText
                it.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @Singleton
    @Provides
    fun providesNotificationMafkirPermissionsValidator(app: Application): MafkirPermissionsValidator {
        return MafkirPermissionsValidator(app)
    }

    @Singleton
    @Provides
    fun providesActionBarController(): ActionBarController {
        return ActionBarController()
    }
}