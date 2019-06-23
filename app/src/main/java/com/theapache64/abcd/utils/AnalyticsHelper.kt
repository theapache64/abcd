package com.theapache64.abcd.utils

import com.jaredrummler.android.device.DeviceName
import com.theah64.safemail.SafeMail

object AnalyticsHelper {

    private const val FROM_MAIL = "mymailer64@gmail.com"
    private const val DEVELOPER_MAIL = "theapache64@gmail.com"

    fun pollMapSubmission() {

        val message = "Map submission success from : ${DeviceName.getDeviceName()}"

        SafeMail.sendMail(
            FROM_MAIL,
            DEVELOPER_MAIL,
            "Map Submission",
            message
        )
    }

    fun pollStyleSubmission() {

        val message = "Style submission success from : ${DeviceName.getDeviceName()}"

        SafeMail.sendMail(
            FROM_MAIL,
            DEVELOPER_MAIL,
            "Style Submission",
            message
        )
    }

    fun pollShareSubmission() {

        val message = "Shared from : ${DeviceName.getDeviceName()}"

        SafeMail.sendMail(
            FROM_MAIL,
            DEVELOPER_MAIL,
            "Share",
            message
        )

    }
}