package com.example.mafkir.di

import com.example.mafkir.notifications.NotificationsService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {
    @ContributesAndroidInjector
    abstract fun bindNotificationsService(): NotificationsService
}