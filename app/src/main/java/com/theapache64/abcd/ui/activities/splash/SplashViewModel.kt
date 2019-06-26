package com.theapache64.abcd.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.getpubprefs.GetPublicPreferencesRequest
import com.theapache64.abcd.data.repositories.AppRepository
import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val publicPrefRequest = MutableLiveData<GetPublicPreferencesRequest>()

    private val publicPrefResponse = Transformations.switchMap(publicPrefRequest) {
        appRepository.getPublicPreferences()
    }

    fun getPublicPref() = publicPrefResponse

    private val launchActivityEvent = SingleLiveEvent<Int>()

    fun getLaunchActivityEvent(): LiveData<Int> {
        return launchActivityEvent
    }

    fun goToNextScreen() {

        val activityId = DrawActivity.ID

        // passing id with the finish notification
        launchActivityEvent.notifyFinished(activityId)
    }

    fun loadPublicPrefs() {
        this.publicPrefRequest.value = GetPublicPreferencesRequest()
    }

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
    }

}