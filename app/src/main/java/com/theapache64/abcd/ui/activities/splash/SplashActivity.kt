package com.theapache64.abcd.ui.activities.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)

        // Watching activity launch command
        viewModel.getLaunchActivityEvent().observe(this, Observer { activityId ->

            when (activityId) {
                DrawActivity.ID -> {
                    startActivity(DrawActivity.getStartIntent(this))
                }

                else -> throw IllegalArgumentException("Undefined activity id $activityId")
            }

            finish()

        })

        // Starting splash timer
        Handler().postDelayed({
            viewModel.goToNextScreen()
        }, SPLASH_DURATION)

    }


    companion object {
        private const val SPLASH_DURATION = 1000L
    }

}
