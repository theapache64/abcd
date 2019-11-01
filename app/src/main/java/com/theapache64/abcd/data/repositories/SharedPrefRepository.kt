package com.theapache64.abcd.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_LAST_SELECTED_CATEGORY = "last_sel_cat"
        private const val KEY_USAGE_COUNT = "usage_count"
        private const val KEY_IS_INITIAL_ASK_DONE = "is_init_ask_done"
        private const val KEY_IS_THRESHOLD_ASK_DONE = "is_threshold_ask_done"
    }

    fun getLastSelectedCategoryName(): String? =
        sharedPreferences.getString(KEY_LAST_SELECTED_CATEGORY, null)

    fun saveLastSelectedCategory(name: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_SELECTED_CATEGORY, name)
        }
    }

    fun incUsageCount() {
        return getUsageCount().let { usageCount ->
            sharedPreferences.edit {
                putInt(KEY_USAGE_COUNT, usageCount + 1)
            }
        }
    }

    fun getUsageCount(): Int {
        return sharedPreferences.getInt(KEY_USAGE_COUNT, 0)
    }

    fun setInitAskDone() {
        sharedPreferences.edit {
            putBoolean(KEY_IS_INITIAL_ASK_DONE, true)
        }
    }

    fun setThresholdAskDone() {
        sharedPreferences.edit {
            putBoolean(KEY_IS_THRESHOLD_ASK_DONE, true)
        }
    }

    fun isInitAskDone(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_INITIAL_ASK_DONE, false)
    }

    fun isThresholdAskDone(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_THRESHOLD_ASK_DONE, false)
    }
}