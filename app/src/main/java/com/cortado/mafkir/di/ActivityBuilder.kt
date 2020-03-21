package com.cortado.mafkir.di

import com.cortado.mafkir.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindMainActivity(): MainActivity
}