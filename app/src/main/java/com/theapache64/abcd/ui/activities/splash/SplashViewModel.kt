package com.theapache64.abcd.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(

) : ViewModel() {

    private val launchActivityEvent = SingleLiveEvent<Int>()

    fun getLaunchActivityEvent(): LiveData<Int> {
        return launchActivityEvent
    }

    fun goToNextScreen() {

        val activityId = DrawActivity.ID

        // passing id with the finish notification
        launchActivityEvent.notifyFinished(activityId)
    }

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
    }

}