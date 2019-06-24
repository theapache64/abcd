package com.theapache64.abcd.utils.extensions

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.theapache64.abcd.R
import com.theapache64.twinkill.logger.info

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

fun Activity.checkFilePermission(onPermissionGranted: () -> Unit) {

    val deniedDialogListener = DialogOnDeniedPermissionListener.Builder.withContext(this)
        .withTitle(R.string.dialog_title_permission)
        .withMessage(R.string.message_external_storage)
        .withButtonText(android.R.string.ok)
        .build()

    val permissionListener = object : BasePermissionListener() {
        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
            info("Permission denied")
        }

        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
            info("Permission granted")
            onPermissionGranted()
        }
    }

    val listener = CompositePermissionListener(deniedDialogListener, permissionListener)

    Dexter.withActivity(this)
        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(listener)
        .check()
}
