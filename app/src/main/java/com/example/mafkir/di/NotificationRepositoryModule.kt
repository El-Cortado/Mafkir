package com.example.mafkir.di

import com.example.mafkir.notifications.NotificationRegistry
import com.example.mafkir.notifications.NotificationRepository
import com.google.common.eventbus.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NotificationRepositoryModule {
    @Singleton
    @Provides
    fun providesNotificationMafkirNotifier(@Named("Notifications") eventBus: EventBus): NotificationRepository {
        val notificationRepository = NotificationRepository()
        eventBus.register(NotificationRegistry(notificationRepository))
        return notificationRepository
    }
}