package com.theapache64.abcd.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.abcd.data.remote.ApiInterface
import com.theapache64.abcd.data.remote.SheetyApiInterface
import com.theapache64.abcd.models.Server
import com.theapache64.twinkill.network.di.modules.BaseNetworkModule
import com.theapache64.twinkill.network.utils.retrofit.adapters.resourceadapter.ResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * To hold all network related objects.
 */
@Module(includes = [BaseNetworkModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiInterface(
        server: Server,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ApiInterface {

        val baseUrl = String.format(BASE_URL, server.ip)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResourceCallAdapterFactory(false))
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideSheetyApiInterface(retrofit: Retrofit): SheetyApiInterface {
        return retrofit.create(SheetyApiInterface::class.java)
    }


    companion object {
        private const val BASE_URL = "http://%s:443/"
    }

}