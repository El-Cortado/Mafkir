package com.cortado.mafkir.di

import androidx.lifecycle.ViewModel
import com.cortado.mafkir.model.MafkirContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MafkirContactViewModel::class)
    abstract fun bindMainViewModel(mafkirContactViewModel: MafkirContactViewModel): ViewModel
}
