package com.example.mafkir.di

import com.google.common.eventbus.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
open class EventBusModule {
    @Singleton
    @Provides @Named("Notifications")
    open fun providesNotificationEventBus():EventBus = EventBus()
}