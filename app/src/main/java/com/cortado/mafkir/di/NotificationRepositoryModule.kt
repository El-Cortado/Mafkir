package com.cortado.mafkir.di

import com.cortado.mafkir.notifications.NotificationRegistry
import com.cortado.mafkir.notifications.NotificationRepository
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