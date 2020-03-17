package com.example.mafkir.di

import android.app.Application
import com.example.mafkir.permissions.MafkirPermissionsValidator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MafkirPermissionsValidatorModule {
    @Singleton
    @Provides
    fun providesNotificationMafkirPermissionsValidator(app: Application): MafkirPermissionsValidator {
        return MafkirPermissionsValidator(app)
    }
}