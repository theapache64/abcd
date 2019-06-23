package com.theapache64.abcd.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.versioninfo.VersionInfoRequest
import com.theapache64.abcd.data.repositories.AppRepository
import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val versionInfoRequest = MutableLiveData<VersionInfoRequest>()

    private val latestVersionInfo = Transformations.switchMap(versionInfoRequest) {
        appRepository.getLatestVersionDetails()
    }

    fun getLatestVersionInfo() = latestVersionInfo

    private val launchActivityEvent = SingleLiveEvent<Int>()

    fun getLaunchActivityEvent(): LiveData<Int> {
        return launchActivityEvent
    }

    fun goToNextScreen() {

        val activityId = DrawActivity.ID

        // passing id with the finish notification
        launchActivityEvent.notifyFinished(activityId)
    }

    fun checkVersion() {
        this.versionInfoRequest.value = VersionInfoRequest()
    }

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
    }

}