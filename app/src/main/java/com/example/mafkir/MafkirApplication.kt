package com.example.mafkir

import android.app.Activity
import android.app.Application
import android.app.Service
import com.example.mafkir.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class MafkirApplication : Application(), HasActivityInjector, HasServiceInjector {

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = mAndroidActivityInjector

    override fun serviceInjector(): DispatchingAndroidInjector<Service> = mAndroidServiceInjector

    @Inject
    lateinit var mAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mAndroidServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().app(this)
            .build().inject(this)
    }
}