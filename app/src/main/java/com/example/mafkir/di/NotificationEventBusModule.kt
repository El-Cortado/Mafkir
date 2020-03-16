package com.example.mafkir.di

import com.google.common.eventbus.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class NotificationEventBusModule {
    @Singleton
    @Provides
    open fun providesNotificationEventBus():EventBus = EventBus()
}