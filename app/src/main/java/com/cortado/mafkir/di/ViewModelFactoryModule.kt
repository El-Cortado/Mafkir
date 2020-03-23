package com.cortado.mafkir.di

import androidx.lifecycle.ViewModelProvider
import com.cortado.mafkir.model.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}