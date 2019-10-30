package com.theapache64.abcd.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_LAST_SELECTED_CATEGORY = "last_sel_cat"
    }

    fun getLastSelectedCategoryName(): String? =
        sharedPreferences.getString(KEY_LAST_SELECTED_CATEGORY, null)

    fun saveLastSelectedCategory(name: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_SELECTED_CATEGORY, name)
        }
    }
}