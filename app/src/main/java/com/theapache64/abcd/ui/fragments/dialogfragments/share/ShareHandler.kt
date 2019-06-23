package com.theapache64.abcd.ui.fragments.dialogfragments.share

import com.theapache64.abcd.ui.base.BaseDialogFragmentHandler

interface ShareHandler : BaseDialogFragmentHandler {
    fun onShareSegMapClicked()
    fun onShareOutputClicked()
    fun onShareSegMapAndOutputClicked()
}