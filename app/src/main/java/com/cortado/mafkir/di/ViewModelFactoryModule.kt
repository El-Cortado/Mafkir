package com.cortado.mafkir.di

import androidx.lifecycle.ViewModelProvider
import com.cortado.mafkir.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    // Method #2
    @Binds
    abstract fun bindViewModelFactory(viewModelProvideFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}