package com.theapache64.abcd.ui.fragments.dialogfragments.brushes

import com.theapache64.abcd.ui.base.BaseDialogFragmentHandler

interface BrushesHandler : BaseDialogFragmentHandler {
    fun onCategorySelected(position: Int)
}