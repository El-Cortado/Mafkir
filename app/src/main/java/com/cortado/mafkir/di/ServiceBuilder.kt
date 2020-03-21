package com.cortado.mafkir.di

import com.cortado.mafkir.notifications.NotificationsService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {
    @ContributesAndroidInjector
    abstract fun bindNotificationsService(): NotificationsService
}