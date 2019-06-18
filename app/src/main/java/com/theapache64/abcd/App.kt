package com.theapache64.abcd


import android.app.Activity
import android.app.Application
import com.theapache64.abcd.data.repositories.ServerRepository
import com.theapache64.abcd.di.components.DaggerAppComponent
import com.theapache64.abcd.models.Server
import com.theapache64.twinkill.TwinKill
import com.theapache64.twinkill.googlefonts.GoogleFonts
import com.theapache64.twinkill.network.di.modules.BaseNetworkModule
import com.theapache64.twinkill.network.utils.retrofit.interceptors.CurlInterceptor
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var serverRepository: ServerRepository


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        // Dagger
        DaggerAppComponent.builder()
            .baseNetworkModule(BaseNetworkModule(BASE_URL))
            .build()
            .inject(this)

        // TwinKill
        TwinKill.init(
            TwinKill
                .builder()
                .setNeedDeepCheckOnNetworkResponse(true)
                .addOkHttpInterceptor(CurlInterceptor())
                .setDefaultFont(GoogleFonts.GoogleSansRegular)
                .build()
        )
    }


    companion object {
        private const val BASE_URL = "http://theapache64.com/mock_api/get_json/jaba/"
    }
}
