package com.theapache64.abcd.ui.activities.splash

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.BuildConfig
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivitySplashBinding
import com.theapache64.abcd.ui.activities.draw.DrawActivity
import com.theapache64.abcd.utils.extensions.showErrorDialog
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivitySplashBinding>(R.layout.activity_splash)

        // Changing progress color
        binding.clpbSplash.indeterminateDrawable.setColorFilter(
            Color.WHITE,
            PorterDuff.Mode.SRC_IN
        )

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

        // Watching for version info
        viewModel.getPublicPref().observe(this, Observer {
            when (it.status) {

                Resource.Status.LOADING -> {
                    info("Getting version info..")
                }

                Resource.Status.SUCCESS -> {

                    binding.clpbSplash.hide()

                    // Checking version info
                    it.data!!.data!!.prefs.apply {
                        // down check
                        if (isDown) {
                            showDownDialog(downReason)
                        } else {
                            //version check
                            if (BuildConfig.VERSION_CODE < latestVersionCode) {
                                // old version
                                showUpdateDialog(latestVersionMessage)
                            } else {
                                // up to date version
                                viewModel.goToNextScreen()
                            }
                        }
                    }

                }

                Resource.Status.ERROR -> {
                    binding.clpbSplash.hide()
                    showErrorDialog(getString(R.string.error_version_check, it.message)) {
                        finish()
                    }
                }
            }
        })

        // Starting splash timer
        Handler().postDelayed({
            viewModel.loadPublicPrefs()
        }, SPLASH_DURATION)

    }

    private fun showDownDialog(downReason: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_maintenance)
            .setMessage(downReason)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            .create()
            .show()
    }

    private fun showUpdateDialog(versionExpiryMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_new_version)
            .setMessage(versionExpiryMessage)
            .setCancelable(false)
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                finish()
            }
            .setPositiveButton(R.string.action_update) { _, _ ->
                // go to release page
                goToReleasePage()
                finish()
            }
            .create()
            .show()
    }

    private fun goToReleasePage() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        startActivity(intent)
    }


    companion object {
        private const val SPLASH_DURATION = 200L
    }

}
