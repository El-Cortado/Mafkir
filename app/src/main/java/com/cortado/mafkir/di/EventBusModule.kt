package com.cortado.mafkir.di

import com.google.common.eventbus.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class EventBusModule {
    @Singleton
    @Provides @Named("Notifications")
    fun providesNotificationEventBus():EventBus = EventBus()
}