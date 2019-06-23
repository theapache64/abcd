package com.theapache64.abcd.utils.extensions

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.theapache64.abcd.R

fun Activity.showErrorDialog(message: String, onOk: (() -> Unit)? = null) {

    val okCallback = if (onOk != null) {
        DialogInterface.OnClickListener { _, _ ->
            onOk()
        }
    } else {
        null
    }

    AlertDialog.Builder(this)
        .setTitle(R.string.title_error)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, okCallback)
        .create()
        .show()
}