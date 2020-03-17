package com.example.mafkir.di

import android.app.Application
import com.example.mafkir.MafkirApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ActivityBuilder::class,
        ServiceBuilder::class,
        EventBusModule::class,
        MafkirNotifierModule::class,
        MafkirPermissionsValidatorModule::class,
        NotificationRepositoryModule::class]
)
interface AppComponent : AndroidInjector<MafkirApplication> {
    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }
}