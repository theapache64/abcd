package com.theapache64.abcd.ui.fragments.dialogfragments.brushcategories

import com.theapache64.abcd.ui.base.BaseDialogFragmentHandler

interface BrushCategoriesHandler : BaseDialogFragmentHandler {
    fun onCategorySelected(position: Int)
}