package com.cortado.mafkir.di

import com.cortado.mafkir.ui.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment
}