package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.data.remote.ApiInterface
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    fun getPublicPreferences() = apiInterface.getPublicPreferences()
}