package com.theapache64.abcd.ui.fragments.dialogfragments.artstyles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.StyleRepository
import com.theapache64.abcd.models.Style
import javax.inject.Inject

class ArtStylesViewModel @Inject constructor(
    styleRepository: StyleRepository
) : ViewModel() {
    private val artStyles = MutableLiveData(styleRepository.artStyles)
    fun getArtStyles(): LiveData<List<Style>> = artStyles
}