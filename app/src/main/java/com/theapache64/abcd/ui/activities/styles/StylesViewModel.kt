package com.theapache64.abcd.ui.activities.styles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.StyleRepository
import com.theapache64.abcd.models.Style
import javax.inject.Inject

class StylesViewModel @Inject constructor(
    stylesRepository: StyleRepository
) : ViewModel() {

    private val styles = MutableLiveData(stylesRepository.styles)
    fun getStyles(): LiveData<List<Style>> = styles

    private val artStyles = MutableLiveData(stylesRepository.artStyles)
    fun getArtStyles(): LiveData<List<Style>> = artStyles

}