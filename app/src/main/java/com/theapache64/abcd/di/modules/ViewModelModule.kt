package com.theapache64.abcd.di.modules

import androidx.lifecycle.ViewModel
import com.theapache64.abcd.ui.activities.draw.DrawViewModel
import com.theapache64.abcd.ui.activities.result.ResultViewModel
import com.theapache64.abcd.ui.activities.splash.SplashViewModel
import com.theapache64.abcd.ui.activities.styles.StylesViewModel
import com.theapache64.abcd.ui.fragments.dialogfragments.artstyles.ArtStylesViewModel
import com.theapache64.abcd.ui.fragments.dialogfragments.brushes.BrushesViewModel
import com.theapache64.abcd.ui.fragments.dialogfragments.brushsize.BrushSizeViewModel
import com.theapache64.abcd.ui.fragments.dialogfragments.share.ShareViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(BrushSizeViewModel::class)
    abstract fun bindBrushSizeViewModel(viewModel: BrushSizeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StylesViewModel::class)
    abstract fun bindStylesViewModel(viewModel: StylesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    abstract fun bindResultViewModel(viewModel: ResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArtStylesViewModel::class)
    abstract fun bindArtStylesViewModel(viewModel: ArtStylesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShareViewModel::class)
    abstract fun bindShareViewModel(viewModel: ShareViewModel): ViewModel


}