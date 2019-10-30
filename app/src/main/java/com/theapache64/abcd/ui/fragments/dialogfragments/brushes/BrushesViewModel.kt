package com.theapache64.abcd.ui.fragments.dialogfragments.brushes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.data.repositories.SharedPrefRepository
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.models.BrushCategory
import javax.inject.Inject

class BrushesViewModel @Inject constructor(
    private val brushesRepo: BrushRepository,
    private val prefRepo: SharedPrefRepository
) : ViewModel() {

    private val brushes = MutableLiveData<List<Brush>>()
    private val brushCategories = MutableLiveData<List<BrushCategory>>()
    private val activeCategory = MutableLiveData<Pair<Int, BrushCategory>>()

    init {
        val brushCategories = brushesRepo.brushCategories
        this.brushCategories.value = brushCategories
        this.activeCategory.value = prefRepo.getLastSelectedCategoryName().let { catName ->
            if (catName == null) {
                Pair(0, brushCategories.first())
            } else {
                val cat = brushCategories.find { it.name == catName }!!
                val catIndex = brushCategories.indexOf(cat)
                Pair(catIndex, cat)
            }
        }
    }

    fun getBrushes(): LiveData<List<Brush>> = brushes

    fun onCategoryChanged(brushCategory: BrushCategory) {

        this.brushes.value =
            brushesRepo.brushCategories.find { it == brushCategory }!!.brushes

        prefRepo.saveLastSelectedCategory(brushCategory.name)
    }

    fun getBrushCategories(): LiveData<List<BrushCategory>> = brushCategories
    private var checkCount = 0
    fun isManualSelection(): Boolean {
        checkCount++
        return checkCount > 1
    }

    fun getActiveCategory(): LiveData<Pair<Int, BrushCategory>> = activeCategory
}