package com.cortado.mafkir.di

import com.cortado.mafkir.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ViewModelModule::class, FragmentBuilder::class])
    abstract fun bindMainActivity(): MainActivity
}