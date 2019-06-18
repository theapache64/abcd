package com.theapache64.abcd.di.modules


import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.abcd.ui.activities.splash.SplashActivity
import com.theapache64.abcd.ui.fragments.BrushesFragment
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