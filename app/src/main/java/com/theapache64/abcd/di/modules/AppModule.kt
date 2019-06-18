package com.theapache64.abcd.di.modules

import android.app.Application

import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        ActivitiesBuilderModule::class,
        ServerModule::class,
        FragmentsBuilderModule::class
    ]
)
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return this.application
    }

}