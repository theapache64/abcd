package com.theapache64.abcd.ui.fragments.dialogfragments.brushcategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.data.repositories.SharedPrefRepository
import com.theapache64.abcd.models.BrushCategory
import javax.inject.Inject

class BrushCategoriesViewModel @Inject constructor(
    private val brushesRepo: BrushRepository,
    private val prefRepo: SharedPrefRepository
) : ViewModel() {

    private val brushCategories = MutableLiveData<Pair<Int, List<BrushCategory>>>()

    init {
        val brushCategories = brushesRepo.brushCategories
        val activeCatPos = prefRepo.getLastSelectedCategoryName().let { catName ->
            if (catName == null) {
                0
            } else {
                val cat = brushCategories.find { it.name == catName }!!
                val catIndex = brushCategories.indexOf(cat)
                catIndex
            }
        }
        this.brushCategories.value = Pair(
            activeCatPos,
            brushCategories
        )
    }

    fun getBrushCategories(): LiveData<Pair<Int, List<BrushCategory>>> = brushCategories
    fun saveSelectedCategory(position: Int) {
        val category = this.brushCategories.value!!.second[position]
        prefRepo.saveLastSelectedCategory(category.name)
    }

}