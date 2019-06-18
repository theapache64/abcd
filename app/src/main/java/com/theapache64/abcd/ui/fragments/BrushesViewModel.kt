package com.theapache64.abcd.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.models.Brush
import javax.inject.Inject

class BrushesViewModel @Inject constructor(
    brushesRepository: BrushRepository
) : ViewModel() {

    private val brushes = MutableLiveData<List<Brush>>()

    init {
        brushes.value = brushesRepository.brushes
    }

    fun getBrushes(): LiveData<List<Brush>> = brushes
}