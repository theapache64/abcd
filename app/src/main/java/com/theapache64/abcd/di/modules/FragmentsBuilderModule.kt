package com.theapache64.abcd.di.modules


import com.theapache64.abcd.ui.fragments.dialogfragments.artstyles.ArtStylesDialogFragment
import com.theapache64.abcd.ui.fragments.dialogfragments.brushes.BrushesDialogFragment
import com.theapache64.abcd.ui.fragments.dialogfragments.brushsize.BrushSizeDialogFragment
import com.theapache64.abcd.ui.fragments.dialogfragments.share.ShareDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * To hold fragments to support AndroidInjection call from dagger.
 */
@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun getBrushesFragment(): BrushesDialogFragment

    @ContributesAndroidInjector
    abstract fun getBrushSizeDialogFragment(): BrushSizeDialogFragment

    @ContributesAndroidInjector
    abstract fun getArtStylesDialogFragment(): ArtStylesDialogFragment

    @ContributesAndroidInjector
    abstract fun getShareDialogFragment(): ShareDialogFragment

}