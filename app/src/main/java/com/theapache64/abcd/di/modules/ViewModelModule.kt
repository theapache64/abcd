package com.theapache64.abcd.di.modules

import androidx.lifecycle.ViewModel
import com.theapache64.abcd.ui.activities.draw.DrawViewModel
import com.theapache64.abcd.ui.activities.splash.SplashViewModel
import com.theapache64.abcd.ui.fragments.BrushesViewModel
import com.theapache64.twinkill.di.modules.BaseViewModelModule
import com.theapache64.twinkill.utils.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [BaseViewModelModule::class])
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DrawViewModel::class)
    abstract fun bindDrawViewModel(viewModel: DrawViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrushesViewModel::class)
    abstract fun bindBrushesViewModel(viewModel: BrushesViewModel): ViewModel


}