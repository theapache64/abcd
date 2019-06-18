package com.theapache64.abcd.di.modules

import com.theapache64.abcd.data.repositories.ServerRepository
import com.theapache64.abcd.models.Server
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    fun provideServer(serverRepository: ServerRepository): Server {
        return serverRepository.getRandomServer()
    }

}