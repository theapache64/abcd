package com.theapache64.abcd.di.modules


import com.theapache64.abcd.ui.fragments.dialogfragments.brushes.BrushesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * To hold fragments to support AndroidInjection call from dagger.
 */
@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun getBrushesFragment(): BrushesFragment

}