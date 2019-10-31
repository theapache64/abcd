package com.theapache64.abcd.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.theapache64.abcd.models.BrushCategory
import com.theapache64.abcd.ui.fragments.brushes.BrushesFragment

class BrushesPagerAdapter(
    fm: FragmentManager?,
    private val brushCategories: List<BrushCategory>
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val brushCategory = brushCategories[position]
        return BrushesFragment.newInstance(ArrayList(brushCategory.brushes))
    }

    override fun getCount(): Int = brushCategories.size

    override fun getPageTitle(position: Int): CharSequence? {
        val brushCategory = brushCategories[position]
        return brushCategory.name
    }
}