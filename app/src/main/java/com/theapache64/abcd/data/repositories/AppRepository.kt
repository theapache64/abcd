package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.data.remote.SheetyApiInterface
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val sheetyApiInterface: SheetyApiInterface
) {
    fun getLatestVersionDetails() = sheetyApiInterface.getVersionInfo()
}