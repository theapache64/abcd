package com.theapache64.abcd.utils.extensions

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.theapache64.abcd.R

fun Activity.showErrorDialog(message: String) {

    AlertDialog.Builder(this)
        .setTitle(R.string.title_error)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .create()
        .show()
}