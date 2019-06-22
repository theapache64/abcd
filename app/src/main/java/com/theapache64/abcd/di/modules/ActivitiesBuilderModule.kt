package com.theapache64.abcd.di.modules


import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.abcd.ui.activities.result.ResultActivity
import com.theapache64.abcd.ui.activities.splash.SplashActivity
import com.theapache64.abcd.ui.activities.styles.StylesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * To hold activities to support AndroidInjection call from dagger.
 */
@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun getDrawActivity(): DrawActivity

    @ContributesAndroidInjector
    abstract fun getResultActivity(): ResultActivity


    @ContributesAndroidInjector
    abstract fun getStylesActivity(): StylesActivity
}