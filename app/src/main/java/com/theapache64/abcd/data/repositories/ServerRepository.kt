package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.models.Server
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerRepository @Inject constructor() {

    private val servers = listOf(
        Server("Server 1 ", "3.16.206.30"),
        Server("Server 2 ", "52.12.58.174"),
        Server("Server 3 ", " 18.234.114.149")
    )


    /**
     * Returns random server to balance load
     */
    fun getRandomServer(): Server {
        return servers.random()
    }

}