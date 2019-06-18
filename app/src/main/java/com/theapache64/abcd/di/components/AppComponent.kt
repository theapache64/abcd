package com.theapache64.abcd.di.components

import com.theapache64.abcd.App
import com.theapache64.abcd.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    // inject the above given modules into this App class
    fun inject(app: App)
}