package com.theapache64.abcd.ui.base

import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment : DialogFragment() {


    override fun onResume() {
        super.onResume()

        // Making dialog full screen
        val params = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}